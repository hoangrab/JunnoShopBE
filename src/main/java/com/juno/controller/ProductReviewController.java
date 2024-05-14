package com.juno.controller;

import com.juno.DTO.ReviewDTO;
import com.juno.model.ProductReviewModel;
import com.juno.service.impl.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class ProductReviewController {
    private final ProductReviewService productReviewService;

    @GetMapping("review")
    public ResponseEntity<?> getProductReview(@RequestParam("idProduct") Long productId) {
        try {
            List<ProductReviewModel> list = productReviewService.findAll(productId);
            return ResponseEntity.ok(list);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("review")
    public ResponseEntity<?> addProductReview(@RequestBody ReviewDTO reviewDTO) {
        try {
            productReviewService.save(reviewDTO);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("review/{id}")
    public ResponseEntity<?> deleteProductReview(@PathVariable Long id) {
        try {
            productReviewService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
