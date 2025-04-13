package pers.zymir.parking.billing.domain.model.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EnterParkFailedEvent extends ApplicationEvent implements DomainEvent {
    private final String plate;

    public EnterParkFailedEvent(Object source, String plate) {
        super(source);
        this.plate = plate;
    }
}
