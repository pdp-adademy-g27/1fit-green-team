package com.example.onefit.subscription;

import com.example.onefit.common.mapper.GenericMapper;
import com.example.onefit.subscription.dto.SubscriptionCreateDto;
import com.example.onefit.subscription.dto.SubscriptionResponseDto;
import com.example.onefit.subscription.dto.SubscriptionUpdateDto;
import com.example.onefit.subscription.entity.Subscription;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionDtoMapper extends GenericMapper<Subscription, SubscriptionCreateDto, SubscriptionResponseDto, SubscriptionUpdateDto> {


    private final ModelMapper mapper;

    @Override
    protected ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public Subscription toEntity(SubscriptionCreateDto subscriptionCreateDto) {
        return mapper.map(subscriptionCreateDto , Subscription.class);

    }

    @Override
    public SubscriptionResponseDto toResponse(Subscription subscription) {
        return mapper.map(subscription, SubscriptionResponseDto.class);
    }

    @Override
    public void toUpdate(SubscriptionUpdateDto subscriptionUpdateDto, Subscription subscription) {
        mapper.map(subscriptionUpdateDto , subscription) ;
    }

}