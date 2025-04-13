package pers.zymir.parking.billing.domain.model.aggregate;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingAggregate {

    // 车牌号
    private String plate;

    // 入场时间
    private LocalDateTime enterTime;

    // 出场时间
    private LocalDateTime leaveTime;

    // 最后一次支付时间
    @Getter
    private LocalDateTime lastPaidTime;

    // 已支付费用总额
    @Getter
    private Integer paidAmount;

    public boolean currentInPark() {
        return enterTime != null;
    }

    public void enterPark() {
        this.enterTime = LocalDateTime.now();
    }

    public Integer getParkingSeconds(LocalDateTime leaveTime) {
        return Math.toIntExact(ChronoUnit.SECONDS.between(this.enterTime, leaveTime));
    }

    public boolean hasPaid() {
        return lastPaidTime != null;
    }
}
