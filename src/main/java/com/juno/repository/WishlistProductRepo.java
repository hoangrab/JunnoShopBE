package com.juno.repository;

import com.juno.entity.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistProductRepo extends JpaRepository<WishlistProduct, Long> {
    List<WishlistProduct> findAllByWishlistId(Long userId);

    void deleteByWishlistIdAndProductId(Long wishlistId, Long productId);

    Optional<WishlistProduct> findByWishlistIdAndProductId(Long wishlistId, Long productId);
}
