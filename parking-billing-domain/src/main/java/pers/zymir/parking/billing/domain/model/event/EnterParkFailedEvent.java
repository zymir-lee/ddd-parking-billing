package pers.zymir.parking.billing.domain.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnterParkFailedEvent implements DomainEvent {
    private final String plate;
}
