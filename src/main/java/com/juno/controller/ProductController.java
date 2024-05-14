package com.juno.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juno.DTO.ProductDTO;
import com.juno.entity.Product;
import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.exception.ResourceNotFoundException;
import com.juno.service.impl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("products")
    public ResponseEntity<?> findAllProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                             @RequestParam(value = "color",required = false) String color,
                                             @RequestParam(value = "psize",required = false) String psize) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("ok nha con");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("product/{id}")
    public ResponseEntity<?> findProductById(@PathVariable("id") int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("ok nha con");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("product")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductDTO product) {
        try {
//            productService.saveProduct(product);
            System.out.println(product.getFieldProducts());
            return ResponseEntity.status(HttpStatus.OK).body("ok nha con");
        }catch (ResourceAlreadyExitsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("product/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        try {
            productService.updateProduct(productDTO,id);
            return ResponseEntity.status(HttpStatus.OK).body("ok nha con");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body("ok nha con");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
