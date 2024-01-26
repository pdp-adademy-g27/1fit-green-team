package com.example.onefit.location;

import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.location.entity.Location;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends GenericRepository<Location, UUID> {
}
