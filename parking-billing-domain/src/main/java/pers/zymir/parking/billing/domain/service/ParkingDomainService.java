package pers.zymir.parking.billing.domain.service;

import pers.zymir.parking.billing.domain.model.command.CalcParkingFeeCommand;
import pers.zymir.parking.billing.domain.model.command.EnterParkCommand;
import pers.zymir.parking.billing.domain.model.command.LeaveParkCommand;
import pers.zymir.parking.billing.domain.model.command.NotifyPaidCommand;

public interface ParkingDomainService {

    void enterPark(EnterParkCommand enterParkCommand);

    Boolean leavePark(LeaveParkCommand leaveParkCommand);

    Integer calcParkingFeeNow(CalcParkingFeeCommand calcParkingFeeCommand);

    void notifyPaid(NotifyPaidCommand notifyPaidCommand);
}