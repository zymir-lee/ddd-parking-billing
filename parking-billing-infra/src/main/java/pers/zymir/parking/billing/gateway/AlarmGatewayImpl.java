package pers.zymir.parking.billing.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pers.zymir.parking.billing.domain.adapter.gateway.AlarmGateway;

@Slf4j
@Component
public class AlarmGatewayImpl implements AlarmGateway {
    @Override
    public void sendEnterFailedAlarm(String plate) {
        log.info("车辆 [{}] 入场失败！请检查", plate);
    }

    @Override
    public void sendLeaveFailedAlarm(String plate) {
        log.info("车辆 [{}] 出场失败！请检查", plate);
    }
}
