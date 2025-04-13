package pers.zymir.parking.billing.domain.model.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotifyPaidCommand {
    private String plate;
    private LocalDateTime paidTime;
    private Integer paidAmount;
}
