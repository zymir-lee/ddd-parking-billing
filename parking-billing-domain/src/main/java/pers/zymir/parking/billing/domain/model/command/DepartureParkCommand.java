package pers.zymir.parking.billing.domain.model.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class DepartureParkCommand {
    private String plate;
    private LocalDateTime departureParkTime;
}
