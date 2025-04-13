package pers.zymir.parking.billing.domain.service;

import pers.zymir.parking.billing.domain.model.command.CalcParkingFeeCommand;
import pers.zymir.parking.billing.domain.model.command.EnterParkCommand;
import pers.zymir.parking.billing.domain.model.command.LeaveParkCommand;
import pers.zymir.parking.billing.domain.model.command.NotifyPaidCommand;
import pers.zymir.parking.billing.domain.model.event.EnterParkFailedEvent;
import pers.zymir.parking.billing.domain.model.event.LeaveParkFailedEvent;

public interface ParkingDomainService {

    void enterPark(EnterParkCommand enterParkCommand);

    Boolean leavePark(LeaveParkCommand leaveParkCommand);

    Integer calcParkingFeeNow(CalcParkingFeeCommand calcParkingFeeCommand);

    void notifyPaid(NotifyPaidCommand notifyPaidCommand);

    void onEnterParkFailed(EnterParkFailedEvent enterParkFailedEvent);

    void onLeaveParkFailed(LeaveParkFailedEvent leaveParkFailedEvent);
}