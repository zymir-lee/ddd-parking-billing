package pers.zymir.parking.billing.domain.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PaidEvent implements DomainEvent {
    private String plate;
    private LocalDateTime paidTime;
    private Integer paidAmount;
}