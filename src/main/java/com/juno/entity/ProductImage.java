package com.juno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imageUrl;

    @Column
    private String idUrl;

    @ManyToOne
    private Product product;

    public ProductImage(String imageUrl, String idUrl) {
        this.imageUrl = imageUrl;
        this.idUrl = idUrl;
    }
}
