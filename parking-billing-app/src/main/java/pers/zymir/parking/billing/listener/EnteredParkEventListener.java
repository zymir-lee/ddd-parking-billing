package pers.zymir.parking.billing.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pers.zymir.parking.billing.domain.model.event.EnteredParkEvent;

@Slf4j
@Component
public class EnteredParkEventListener {

    @EventListener(value = EnteredParkEvent.class)
    public void handleEnteredParkEvent(EnteredParkEvent enteredParkEvent) {
        log.info("handle entered park event:{}", enteredParkEvent);
    }
}
