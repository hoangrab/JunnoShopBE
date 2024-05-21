package com.juno.model;

import com.juno.entity.Category;
import com.juno.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    private Long id;
    private String name;
    private String description;
    private boolean flashSale;
    private Long originalPrice;
    private Long salePrice;
    private Category category;
    private List<ProductImage> productImages;
    private List<ProductItemModel> productItems;
    private List<ProductReviewModel> productReviews;
}
