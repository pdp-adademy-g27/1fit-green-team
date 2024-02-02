package com.example.onefit.subscription;


import com.example.onefit.common.service.GenericService;
import com.example.onefit.subscription.dto.SubscriptionCreateDto;
import com.example.onefit.subscription.dto.SubscriptionResponseDto;
import com.example.onefit.subscription.dto.SubscriptionUpdateDto;
import com.example.onefit.subscription.entity.Subscription;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.example.onefit.common.variable.ExcMessage.SUBSCRIPTION_NOTFOUND;

@Getter
@Service
@RequiredArgsConstructor
public class SubscriptionService extends GenericService<UUID, Subscription, SubscriptionResponseDto, SubscriptionCreateDto, SubscriptionUpdateDto> {
    private final SubscriptionRepository repository;
    private final Class<Subscription> entityClass = Subscription.class;
    private final SubscriptionDtoMapper mapper;


    @Transactional
    @Override
    public SubscriptionResponseDto internalCreate(SubscriptionCreateDto subscriptionCreateDto) {
        Subscription subscription = mapper.toEntity(subscriptionCreateDto);
        subscription.setId(UUID.randomUUID());
        repository.save(subscription);
        return mapper.toResponse(subscription);
    }

    @Transactional
    @Override
    public SubscriptionResponseDto internalUpdate(SubscriptionUpdateDto subscriptionUpdateDto, UUID id) {
        Subscription subscription = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException(SUBSCRIPTION_NOTFOUND.formatted(id)));
        mapper.toUpdate(subscriptionUpdateDto, subscription);
        Subscription saved = repository.save(subscription);
        return mapper.toResponse(saved);
    }
}
