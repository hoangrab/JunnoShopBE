package com.juno.controller;

import com.juno.entity.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class BrandController {

    @GetMapping("focus")
    public ResponseEntity<?> getBrands() {
        try {
            return ResponseEntity.ok(false);
        }catch (Exception ex) {
            return null;
        }
    }
}
