package pers.zymir.parking.billing.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pers.zymir.parking.billing.domain.adapter.gateway.AlarmGateway;
import pers.zymir.parking.billing.domain.adapter.gateway.EventPublisher;
import pers.zymir.parking.billing.domain.adapter.repository.ParkingRepository;
import pers.zymir.parking.billing.domain.model.aggregate.ParkingAggregate;
import pers.zymir.parking.billing.domain.model.command.CalcParkingFeeCommand;
import pers.zymir.parking.billing.domain.model.command.EnterParkCommand;
import pers.zymir.parking.billing.domain.model.command.LeaveParkCommand;
import pers.zymir.parking.billing.domain.model.command.NotifyPaidCommand;
import pers.zymir.parking.billing.domain.model.event.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class ParkingDomainServiceImpl implements ParkingDomainService {

    private static final Integer PARK_FEE_PER_HOUR = 1000;
    private static final Integer ONE_HOUR_SECOND = Math.toIntExact(TimeUnit.HOURS.toSeconds(1));

    private final ParkingRepository parkingRepository;
    private final EventPublisher eventPublisher;
    private final AlarmGateway alarmGateway;

    @Override
    public void enterPark(EnterParkCommand enterParkCommand) {
        String plate = enterParkCommand.getPlate();
        ParkingAggregate parkingAggregate = parkingRepository.findById(plate);

        if (parkingAggregate.currentInPark()) {
            eventPublisher.publish(new EnterParkFailedEvent(plate));
            throw new RuntimeException(String.format("车牌号 [%s] 已在场，不得重复入场", plate));
        }

        parkingAggregate.enterPark();
        parkingRepository.save(parkingAggregate);
        eventPublisher.publish(new EnteredParkEvent(plate));
    }

    @Override
    public Boolean leavePark(LeaveParkCommand leaveParkCommand) {
        String plate = leaveParkCommand.getPlate();
        ParkingAggregate parkingAggregate = parkingRepository.findById(plate);

        if (!parkingAggregate.currentInPark()) {
            eventPublisher.publish(new LeaveParkFailedEvent(plate));
            throw new RuntimeException(String.format("车牌号 [%s] 未在场，不得出场", plate));
        }

        // 这里用判断方法签名用：“是否可出场” 还是 计算费用然后进行比较呢？
        //  当前的实现方式提取了领域服务，优先将业务规则放在领域服务内 而不是聚合内部 使得代码能够反应业务
        Integer parkingFeeNow = this.calcParkingFeeNow(parkingAggregate, leaveParkCommand.getLeaveTime());
        if (parkingFeeNow > 0) {
            return false;
        }

        eventPublisher.publish(LeaveParkEvent.of(plate));
        return true;
    }

    @Override
    public Integer calcParkingFeeNow(CalcParkingFeeCommand calcParkingFeeCommand) {
        String plate = calcParkingFeeCommand.getPlate();
        ParkingAggregate parkingAggregate = parkingRepository.findById(plate);
        if (!parkingAggregate.currentInPark()) {
            throw new RuntimeException(String.format("车牌号 [%s] 未在场", plate));
        }

        return calcParkingFeeNow(parkingAggregate, calcParkingFeeCommand.getLeaveTime());
    }

    @Override
    public void notifyPaid(NotifyPaidCommand notifyPaidCommand) {
        String plate = notifyPaidCommand.getPlate();
        ParkingAggregate parkingAggregate = parkingRepository.findById(plate);
        if (!parkingAggregate.currentInPark()) {
            throw new RuntimeException(String.format("车牌号 [%s] 未在场，不能进行支付", plate));
        }

        parkingAggregate.pay(notifyPaidCommand.getPaidAmount(), notifyPaidCommand.getPaidTime());
        parkingRepository.save(parkingAggregate);
        eventPublisher.publish(new PaidEvent(plate, notifyPaidCommand.getPaidTime(), notifyPaidCommand.getPaidAmount()));
    }

    @Override
    public void onEnterParkFailed(EnterParkFailedEvent enterParkFailedEvent) {
        alarmGateway.sendEnterFailedAlarm(enterParkFailedEvent.getPlate());
    }

    @Override
    public void onLeaveParkFailed(LeaveParkFailedEvent leaveParkFailedEvent) {
        alarmGateway.sendLeaveFailedAlarm(leaveParkFailedEvent.getPlate());
    }


    /**
     * 基于离场时间计算剩余应缴费用
     * <p>
     * 为什么不直接通过调用聚合来计算？-> 因为想将业务逻辑/规则放在领域服务当中
     * 如果放到聚合当中 “不足1h按照1h算” 这个业务规则，就在当前方法中体现不出来了
     *
     * @param parkingAggregate 当前停车聚合
     * @param leaveTime        离场时间
     * @return 金额 单位分
     */
    private Integer calcParkingFeeNow(ParkingAggregate parkingAggregate, LocalDateTime leaveTime) {
        boolean hasPaid = parkingAggregate.hasPaid();

        int shouldPaidAmount = parkingHours(parkingAggregate, leaveTime) * PARK_FEE_PER_HOUR;
        if (!hasPaid) {
            return shouldPaidAmount;
        }

        //  15分钟之内免费 （假定每次支付都是足额缴纳）
        long lastPaidMinutes = ChronoUnit.MINUTES.between(parkingAggregate.getLastPaidTime(), leaveTime);
        if (lastPaidMinutes <= 15) {
            return 0;
        }

        return shouldPaidAmount - parkingAggregate.getPaidAmount();
    }

    private int parkingHours(ParkingAggregate parkingAggregate, LocalDateTime leaveTime) {
        Integer parkingSeconds = parkingAggregate.getParkingSeconds(leaveTime);
        int hours = parkingSeconds / ONE_HOUR_SECOND;

        // 不足1h按照1h算
        return parkingSeconds % ONE_HOUR_SECOND > 0 ? hours + 1 : hours;
    }
}