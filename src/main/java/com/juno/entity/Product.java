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

    private String description;

    @ManyToOne
    private Brand brand;

    @OneToMany(mappedBy = "productRv")
    private List<ProductReview> productReviews;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Wishlist wishlist;

    @OneToMany(mappedBy = "productIt")
    private List<ProductItem> productItems;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;
}
