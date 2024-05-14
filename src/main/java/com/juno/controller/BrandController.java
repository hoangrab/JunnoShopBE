package com.juno.controller;

import com.juno.DTO.BrandDTO;
import com.juno.entity.Brand;
import com.juno.service.impl.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping("brands")
    public ResponseEntity<?> getAllBrands() {
        try {
            List<Brand> list = brandService.getAllBrands();
            return ResponseEntity.ok(list);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("brand/{id}")
    public ResponseEntity<?> addBrand(@ModelAttribute BrandDTO brandDTO) {
        try {
            brandService.saveBrand(brandDTO);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("brand/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @ModelAttribute BrandDTO brandDTO) {
        try {
            brandService.updateBrand(brandDTO, id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("brand/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        try {
            brandService.deleteBrandById(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
