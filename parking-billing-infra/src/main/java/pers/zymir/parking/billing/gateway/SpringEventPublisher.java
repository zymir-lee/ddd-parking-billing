package pers.zymir.parking.billing.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pers.zymir.parking.billing.domain.adapter.gateway.EventPublisher;
import pers.zymir.parking.billing.domain.model.event.DomainEvent;

@Component
@RequiredArgsConstructor
public class SpringEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent domainEvent) {
        applicationEventPublisher.publishEvent(domainEvent);
    }
}
