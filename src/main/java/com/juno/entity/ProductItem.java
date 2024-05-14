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
@Table(name = "product_item")
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long originalPrice;

    @Column
    private Long sale_price;

    @ManyToOne
    @JoinColumn(name = "color_id",referencedColumnName = "id")
    private Color color;

    @Column
    private int quantity;

    @ManyToOne
    private SizeOption size_option;

    @ManyToOne
    private Product productIt;
}
