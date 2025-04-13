package pers.zymir.parking.billing.domain.adapter.gateway;

public interface AlarmGateway {
    void sendEnterFailedAlarm(String plate);

    void sendLeaveFailedAlarm(String plate);
}
