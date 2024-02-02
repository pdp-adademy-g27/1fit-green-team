package com.example.onefit.subscription;

import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.subscription.entity.Subscription;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends GenericRepository<Subscription, UUID> {
}
