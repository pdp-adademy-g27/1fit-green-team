package com.example.onefit.feedback;

import com.example.onefit.common.service.GenericService;
import com.example.onefit.feedback.dto.FeedBackCreateDto;
import com.example.onefit.feedback.dto.FeedBackResponseDto;
import com.example.onefit.feedback.dto.FeedBackUpdateDto;
import com.example.onefit.feedback.entity.FeedBack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Getter
@AllArgsConstructor
public class FeedBackService extends GenericService<UUID, FeedBack, FeedBackResponseDto, FeedBackCreateDto, FeedBackUpdateDto> {

    private final FeedBackRepository repository;

    private final Class<FeedBack> entityClass = FeedBack.class;

    private final FeedBackMapperDto mapper;


    @Override
    protected FeedBackResponseDto internalCreate(FeedBackCreateDto feedBackCreateDto) {
        FeedBack entity = mapper.toEntity(feedBackCreateDto);
        entity.setId(UUID.randomUUID());
        FeedBack saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    protected FeedBackResponseDto internalUpdate(FeedBackUpdateDto feedBackUpdateDto, UUID uuid) {
        FeedBack feedBack = repository.findById(uuid).orElseThrow();
        feedBack.setCompanyName(feedBackUpdateDto.getCompanyName());
        feedBack.setCity(feedBackUpdateDto.getCity());
        feedBack.setName(feedBackUpdateDto.getName());
        feedBack.setInstagram(feedBackUpdateDto.getInstagram());
        return mapper.toResponse(repository.save(feedBack));
    }
}
