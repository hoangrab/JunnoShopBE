package com.juno.service.impl;

import com.juno.DTO.WishlistDTO;
import com.juno.entity.Product;
import com.juno.entity.Wishlist;
import com.juno.entity.WishlistProduct;
import com.juno.exception.ResourceNotFoundException;
import com.juno.model.ProductModel;
import com.juno.repository.ProductRepo;
import com.juno.repository.WishlistProductRepo;
import com.juno.repository.WishlistRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepo wishlistRepo;
    private final ProductRepo productRepo;
    private final ProductService productService;
    private final WishlistProductRepo wishlistProductRepo;

    public void save(WishlistDTO wishlistDTO) {
        Wishlist wishlist = wishlistRepo.findByUserId(wishlistDTO.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        Product product = productRepo.findById(wishlistDTO.getIdProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        WishlistProduct wishlistProduct = new WishlistProduct();
        wishlistProduct.setWishlist(wishlist);
        wishlistProduct.setProduct(product);
        wishlistProductRepo.save(wishlistProduct);
    }

    public List<ProductModel> getProductInWishlist(Long idUser) {
        Wishlist wishlist = wishlistRepo.findByUserId(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        List<WishlistProduct> wishlistProducts = wishlistProductRepo.findAllByWishlistId(wishlist.getId());
        List<ProductModel> productModels = new ArrayList<>();
        wishlistProducts.forEach(e -> {
            productModels.add(productService.convertEntityToModel(e.getProduct()));
        });
        return productModels;
    }


    public void deleteProductInWishlist(WishlistDTO wishlistDTO) {
        Wishlist wishlist = wishlistRepo.findByUserId(wishlistDTO.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        Product product = productRepo.findById(wishlistDTO.getIdProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        wishlistProductRepo.deleteByWishlistIdAndProductId(wishlist.getId(), product.getId());
    }
}
