package pers.zymir.parking.billing.domain.adapter.gateway;

import pers.zymir.parking.billing.domain.model.event.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent domainEvent);
}
