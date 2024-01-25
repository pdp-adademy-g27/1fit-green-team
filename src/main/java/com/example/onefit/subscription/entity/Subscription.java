package com.example.onefit.subscription.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`subscription`")
public class Subscription {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String days;

    @Column(nullable = false)
    private String freezingDay;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String images;

    private boolean isPopular;


}
