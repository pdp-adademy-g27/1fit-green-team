package com.example.onefit.subscription;


import com.example.onefit.common.service.GenericService;
import com.example.onefit.subscription.dto.SubscriptionCreateDto;
import com.example.onefit.subscription.dto.SubscriptionResponseDto;
import com.example.onefit.subscription.dto.SubscriptionUpdateDto;
import com.example.onefit.subscription.entity.Subscription;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Service
@RequiredArgsConstructor
public class SubscriptionService extends GenericService<UUID,Subscription, SubscriptionResponseDto, SubscriptionCreateDto, SubscriptionUpdateDto>
    {
    private final SubscriptionRepository repository;
    private final Class<Subscription> entityClass = Subscription.class;
    private final SubscriptionDtoMapper mapper;


        @Override
        protected SubscriptionResponseDto internalCreate(SubscriptionCreateDto subscriptionCreateDto) {
            Subscription subscription = mapper.toEntity(subscriptionCreateDto);
            subscription.setId(UUID.randomUUID());
            repository.save(subscription);
            return mapper.toResponse(subscription);
        }

        @Override
        protected SubscriptionResponseDto internalUpdate(SubscriptionUpdateDto subscriptionUpdateDto, UUID uuid) {
            Subscription subscription = repository.findById(uuid).orElseThrow();
            mapper.toUpdate(subscriptionUpdateDto, subscription);
            Subscription saved = repository.save(subscription);
            return mapper.toResponse(saved);
        }
    }
