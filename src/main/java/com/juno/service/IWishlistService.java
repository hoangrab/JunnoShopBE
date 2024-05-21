package com.juno.service;

import com.juno.DTO.WishlistDTO;
import com.juno.model.ProductModel;

import java.util.List;

public interface IWishlistService {
    void save(WishlistDTO wishlistDTO);
    List<ProductModel> getProductInWishlist(Long idUser);
    void deleteProductInWishlist(WishlistDTO wishlistDTO);
}
