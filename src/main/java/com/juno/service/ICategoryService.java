package com.juno.service;

import com.juno.DTO.CategoryDTO;
import com.juno.entity.Category;
import com.juno.model.CategoryModel;

import java.util.List;

public interface ICategoryService {
    public List<CategoryModel> getAllCategories();
    public CategoryModel getById(Long id);
    public void saveCategory(CategoryDTO categoryDTO);
    public void updateCategory(CategoryDTO categoryDTO,Long id);
    public void deleteById(Long id);
    public void convertDTOToEntity(CategoryDTO categoryDTO, Category category);
    public CategoryModel convertEntityToModel(Category category);
}
