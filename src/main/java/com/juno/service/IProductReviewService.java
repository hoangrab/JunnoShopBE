package com.juno.service;

import com.juno.DTO.ReviewDTO;
import com.juno.entity.ProductReview;
import com.juno.model.ProductReviewModel;

public interface IProductReviewService {
    void delete(Long id);
    void save(ReviewDTO reviewDTO);
    ProductReviewModel convertEntityToModel(ProductReview productReview);
}
