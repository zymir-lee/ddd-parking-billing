package pers.zymir.parking.billing.domain.model.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EnteredParkEvent extends ApplicationEvent implements DomainEvent {
    private final String plate;

    public EnteredParkEvent(Object source, String plate) {
        super(source);
        this.plate = plate;
    }
}
