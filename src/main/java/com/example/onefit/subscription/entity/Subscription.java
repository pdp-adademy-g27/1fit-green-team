package com.example.onefit.subscription.entity;

import com.example.onefit.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
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
    private Integer days; // monthly rate: the day of the month is written ...  average 1 month 30 days

    @Column(nullable = false)
    private Integer freezingDay;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String images;

    private boolean isPopular;

    @OneToMany(mappedBy = "subscription")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users;


}
