package com.example.onefit.studio;

import com.example.onefit.common.repository.GenericRepository;
import com.example.onefit.studio.entity.Studio;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudioRepository extends GenericRepository<Studio, UUID> {
}
