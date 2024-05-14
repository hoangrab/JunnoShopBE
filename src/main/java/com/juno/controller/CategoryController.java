package com.juno.controller;

import com.juno.DTO.CategoryDTO;
import com.juno.model.CategoryModel;
import com.juno.service.impl.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("categories")
    public ResponseEntity<?> getAllCategory(@RequestParam(value = "page",defaultValue = "0") int page,
                                            @RequestParam(value = "size",defaultValue = "10") int size) {
        try {
            List<CategoryModel> categoryModels =  categoryService.getAllCategories();
            return new ResponseEntity<>(categoryModels, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {
        try {
            CategoryModel categoryModel = categoryService.getById(id);
            return new ResponseEntity<>(categoryModel, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("category")
    public ResponseEntity<?> createCategory(@Valid @ModelAttribute CategoryDTO categoryDTO) {
        try{
            categoryService.saveCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("category/{id}")
    public ResponseEntity<?> updateCategory(@Valid @ModelAttribute CategoryDTO categoryDTO,
                                            @PathVariable("id") Long id) {
        try {
            categoryService.updateCategory(categoryDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        try {
            categoryService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
