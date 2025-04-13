package pers.zymir.parking.billing.domain.model.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LeaveParkEvent implements DomainEvent {
    private String plate;
    private LocalDateTime leaveTime;

    public static LeaveParkEvent of(String plate) {
        LeaveParkEvent leaveParkEvent = new LeaveParkEvent();
        leaveParkEvent.plate = plate;
        leaveParkEvent.leaveTime = LocalDateTime.now();
        return leaveParkEvent;
    }
}
