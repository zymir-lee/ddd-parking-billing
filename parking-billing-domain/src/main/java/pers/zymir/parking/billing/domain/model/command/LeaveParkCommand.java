package pers.zymir.parking.billing.domain.model.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LeaveParkCommand {
    private String plate;
    private LocalDateTime leaveTime;
}
