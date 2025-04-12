package pers.zymir.parking.billing.domain.adapter.repository;

import pers.zymir.parking.billing.domain.model.aggregate.ParkingAggregate;

public interface ParkingRepository {
    ParkingAggregate findById(String plate);

    void save(ParkingAggregate parkingAggregate);
}