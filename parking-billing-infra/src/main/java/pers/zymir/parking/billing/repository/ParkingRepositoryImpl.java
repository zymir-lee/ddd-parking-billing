package pers.zymir.parking.billing.repository;

import org.springframework.stereotype.Repository;
import pers.zymir.parking.billing.domain.adapter.repository.ParkingRepository;
import pers.zymir.parking.billing.domain.model.aggregate.ParkingAggregate;

@Repository
public class ParkingRepositoryImpl implements ParkingRepository {

    @Override
    public ParkingAggregate findById(String plate) {
        return null;
    }

    @Override
    public void save(ParkingAggregate parkingAggregate) {

    }
}
