package pers.zymir.parking.billing.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pers.zymir.parking.billing.dao.ParkingHistoryMapper;
import pers.zymir.parking.billing.domain.model.event.EnterParkFailedEvent;
import pers.zymir.parking.billing.domain.model.event.LeaveParkEvent;
import pers.zymir.parking.billing.domain.service.ParkingDomainService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnteredParkEventListener {

    private final ParkingHistoryMapper parkingHistoryMapper;
    private final ParkingDomainService parkingDomainService;

    @EventListener(value = LeaveParkEvent.class)
    public void handleLeaveParkEvent(LeaveParkEvent leaveParkEvent) {
        parkingHistoryMapper.updateLeaveParkTime(leaveParkEvent.getPlate(), leaveParkEvent.getLeaveTime());
    }

    @EventListener(value = EnterParkFailedEvent.class)
    public void handleEnterParkFailedEvent(EnterParkFailedEvent enterParkFailedEvent) {
        parkingDomainService.onEnterParkFailed(enterParkFailedEvent);
    }
}
