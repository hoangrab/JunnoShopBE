package com.juno.repository;

import com.juno.entity.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistProductRepo extends JpaRepository<WishlistProduct, Long> {
    List<WishlistProduct> findAllByWishlistId(Long userId);

    void deleteByWishlistIdAndProductId(Long wishlistId, Long productId);
}
