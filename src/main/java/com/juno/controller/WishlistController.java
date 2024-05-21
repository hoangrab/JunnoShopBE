package com.juno.controller;

import com.juno.DTO.WishlistDTO;
import com.juno.entity.Wishlist;
import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.model.ProductModel;
import com.juno.service.impl.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping("wishlist/{id}")
    public ResponseEntity<?> getWishlist(@PathVariable Long id) {
        try {
            List<ProductModel> list = wishlistService.getProductInWishlist(id);
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("wishlist")
    public ResponseEntity<?> addWishlist(@RequestBody WishlistDTO wishlist) {
        try {
            wishlistService.save(wishlist);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (ResourceAlreadyExitsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("wishlist")
    public ResponseEntity<?> deleteWishlist(@RequestBody WishlistDTO wishlist) {
        try {
            wishlistService.deleteProductInWishlist(wishlist);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
