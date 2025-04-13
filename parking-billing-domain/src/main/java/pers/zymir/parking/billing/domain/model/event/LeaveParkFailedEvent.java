package pers.zymir.parking.billing.domain.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeaveParkFailedEvent implements DomainEvent {
    private String plate;
}