package com.juno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private boolean flash_sale;

    private String description;

    @Column
    private Long original;

    @Column
    private Long sale_price;

    @ManyToOne
    private Brand brand;

    @OneToMany(mappedBy = "productRv")
    private List<ProductReview> productReviews;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<WishlistProduct> wishlistProducts;

    @OneToMany(mappedBy = "productIt")
    private List<ProductItem> productItems;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;
}
