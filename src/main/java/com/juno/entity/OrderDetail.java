package com.juno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_item_id",referencedColumnName = "id")
    private ProductItem productItem;

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private Order orderDt;
}
