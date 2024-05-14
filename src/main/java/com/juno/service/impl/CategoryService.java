package com.juno.service.impl;

import com.juno.DTO.CategoryDTO;
import com.juno.entity.Category;
import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.exception.ResourceNotFoundException;
import com.juno.model.CategoryModel;
import com.juno.repository.CategoryRepository;
import com.juno.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepo;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryModel> getAllCategories() {
        return categoryRepo.findAll().stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryModel getById(Long id) {
        categoryRepo.findById(id).map(this::convertEntityToModel)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Category id: " + id));
        return null;
    }

    @Override
    @Transactional
    public void saveCategory(CategoryDTO categoryDTO) {
        if(categoryRepo.findByName(categoryDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExitsException("Name category is duplicate");
        }
        Optional<Category> parent = categoryRepo.findById(categoryDTO.getParentId());
        if(parent.isEmpty()) {
            throw new ResourceNotFoundException("Id category parent not found");
        }
        Category category = new Category();
        convertDTOToEntity(categoryDTO,category);
        category.setParent(parent.get());
        categoryRepo.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryDTO categoryDTO, Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if(category.isEmpty()) throw new ResourceNotFoundException("Id category not found");
        convertDTOToEntity(categoryDTO,category.get());
        categoryRepo.save(category.get());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if(categoryRepo.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Id category not found");
        }
        categoryRepo.deleteById(id);
    }

    @Override
    public void convertDTOToEntity(CategoryDTO categoryDTO, Category category) {
//        category.setName(categoryDTO.getName());
//        category.setDescription(categoryDTO.getDescription());
//        if(categoryDTO.getLogo() != null) {
//
//        }
    }

    @Override
    public CategoryModel convertEntityToModel(Category category) {
        return new CategoryModel(category.getId(),category.getName(),
                category.getDescription(),category.getParent().getId(),category.getImage());
    }
}
