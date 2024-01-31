package com.example.onefit.category;

import com.example.onefit.category.dto.CategoryCreateDto;
import com.example.onefit.category.dto.CategoryResponseDto;
import com.example.onefit.category.dto.CategoryUpdateDto;
import com.example.onefit.category.entity.Category;
import com.example.onefit.common.service.GenericService;
import com.example.onefit.common.variable.ExcMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class CategoryService extends GenericService<UUID, Category, CategoryResponseDto, CategoryCreateDto, CategoryUpdateDto> {

    private final CategoryRepository repository;
    private final Class<Category> entityClass = Category.class;
    private final CategoryDtoMapper mapper;

    @Override
    protected CategoryResponseDto internalCreate(CategoryCreateDto categoryCreateDto) {
        Category category = mapper.toEntity(categoryCreateDto);
        category.setId(UUID.randomUUID());
        repository.save(category);
        return mapper.toResponse(category);
    }

    @Override
    protected CategoryResponseDto internalUpdate(CategoryUpdateDto categoryUpdateDto, UUID id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExcMessage.CATEGORY_NOTFOUND.formatted(id)));
        mapper.toUpdate(categoryUpdateDto, category);
        repository.save(category);
        return mapper.toResponse(category);
    }

}
