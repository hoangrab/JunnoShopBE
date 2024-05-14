package com.juno.service.impl;

import com.juno.DTO.WishlistDTO;
import com.juno.entity.Product;
import com.juno.entity.Wishlist;
import com.juno.exception.ResourceNotFoundException;
import com.juno.model.ProductModel;
import com.juno.repository.ProductRepo;
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

    public void save(WishlistDTO wishlistDTO) {
        Wishlist wishlist = wishlistRepo.findByUserId(wishlistDTO.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        Product product = productRepo.findById(wishlistDTO.getIdProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        List<Product> list = wishlist.getProductList();
        list.add(product);
        wishlist.setProductList(list);
        wishlistRepo.save(wishlist);
    }

    public List<ProductModel> getProductInWishlist(Long idUser) {
        Wishlist wishlist = wishlistRepo.findByUserId(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        return wishlist.getProductList().stream().map(productService::convertEntityToModel).toList();
    }

    public void deleteProductInWishlist(WishlistDTO wishlistDTO) {
        Wishlist wishlist = wishlistRepo.findByUserId(wishlistDTO.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        List<Product> list = new ArrayList<>();
        wishlist.getProductList().forEach(e -> {
            if(!Objects.equals(e.getId(), wishlistDTO.getIdProduct())) list.add(e);
        });
        wishlist.setProductList(list);
    }
}
