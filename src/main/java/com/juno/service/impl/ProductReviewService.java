package com.juno.service.impl;

import com.juno.DTO.ReviewDTO;
import com.juno.entity.Product;
import com.juno.entity.ProductReview;
import com.juno.entity.User;
import com.juno.exception.ResourceNotFoundException;
import com.juno.model.ProductReviewModel;
import com.juno.repository.ProductRepo;
import com.juno.repository.ProductReviewRepo;
import com.juno.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReviewService {
    private final ProductReviewRepo productReviewRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public List<ProductReviewModel> findAll(Long idProduct) {
        return productReviewRepo.findAllByProductRvId(idProduct).stream()
                .map(this::convertEntityToModel).collect(Collectors.toList());
    }

    @Transactional
    public void save(ReviewDTO reviewDTO) {
        Product product = productRepo.findById(reviewDTO.getIdProduct())
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        User user = userRepo.findById(reviewDTO.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        ProductReview productReview = new ProductReview();
        productReview.setProductRv(product);
        productReview.setUser(user);
        productReview.setRating(reviewDTO.getRating());
        productReview.setContent(productReview.getContent());
        productReviewRepo.save(productReview);
    }

    @Transactional
    public void delete(Long id) {
        productReviewRepo.deleteById(id);
    }

    public ProductReviewModel convertEntityToModel(ProductReview productReview) {
        ProductReviewModel productReviewModel = new ProductReviewModel();
        productReviewModel.setRating(productReview.getRating());
        productReviewModel.setName(productReview.getUser().getFullName());
        productReviewModel.setContent(productReview.getContent());
        return productReviewModel;
    }
}
