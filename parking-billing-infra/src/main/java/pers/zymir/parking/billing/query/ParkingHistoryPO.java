package pers.zymir.parking.billing.query;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingHistoryPO {
    private Long id;
    private String plate;
    private LocalDateTime enterTime;
    private LocalDateTime leaveTime;
    private Integer paidAmount;
}
