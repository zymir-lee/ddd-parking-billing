package pers.zymir.parking.billing.domain.model.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EntryParkCommand {
    private String plate;
}