package com.juno.repository;

import com.juno.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepo extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findAllByProductRvId(long productId);
}
