package pers.zymir.parking.billing.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pers.zymir.parking.billing.dao.ParkingHistoryMapper;
import pers.zymir.parking.billing.domain.model.event.EnteredParkEvent;
import pers.zymir.parking.billing.domain.model.event.LeaveParkEvent;
import pers.zymir.parking.billing.domain.model.event.LeaveParkFailedEvent;
import pers.zymir.parking.billing.domain.service.ParkingDomainService;
import pers.zymir.parking.billing.query.ParkingHistoryPO;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeavedParkEventListener {

    private final ParkingHistoryMapper parkingHistoryMapper;
    private final ParkingDomainService parkingDomainService;

    @EventListener(value = LeaveParkEvent.class)
    public void handleEnteredParkEvent(EnteredParkEvent enteredParkEvent) {
        log.info("handle entered park event:{}", enteredParkEvent);

        ParkingHistoryPO parkingHistoryPO = new ParkingHistoryPO();
        parkingHistoryPO.setPlate(enteredParkEvent.getPlate());
        parkingHistoryPO.setEnterTime(LocalDateTime.now());
        parkingHistoryMapper.insert(parkingHistoryPO);
    }

    @EventListener(value = LeavedParkEventListener.class)
    public void handleLeavedParkEventListener(LeaveParkFailedEvent leaveParkFailedEvent) {
        parkingDomainService.onLeaveParkFailed(leaveParkFailedEvent);
    }

}
