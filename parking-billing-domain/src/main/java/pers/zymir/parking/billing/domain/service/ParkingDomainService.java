package pers.zymir.parking.billing.domain.service;

import pers.zymir.parking.billing.domain.model.command.CalcParkingFeeCommand;
import pers.zymir.parking.billing.domain.model.command.DepartureParkCommand;
import pers.zymir.parking.billing.domain.model.command.EnterParkCommand;

public interface ParkingDomainService {

    void enterPark(EnterParkCommand enterParkCommand);

    Boolean departurePark(DepartureParkCommand departureParkCommand);

    Integer calcParkingFeeNow(CalcParkingFeeCommand calcParkingFeeCommand);
}