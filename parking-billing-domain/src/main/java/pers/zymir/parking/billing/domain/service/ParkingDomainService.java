package pers.zymir.parking.billing.domain.service;

import pers.zymir.parking.billing.domain.model.command.CalcParkingFeeCommand;
import pers.zymir.parking.billing.domain.model.command.DepartureParkCommand;
import pers.zymir.parking.billing.domain.model.command.EntryParkCommand;

public interface ParkingDomainService {

    void entryPark(EntryParkCommand entryParkCommand);

    Boolean departurePark(DepartureParkCommand departureParkCommand);

    Integer calcParkingFeeNow(CalcParkingFeeCommand calcParkingFeeCommand);
}