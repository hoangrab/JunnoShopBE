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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String note;

    @Column
    private String status;

    @Column
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User userOrd;

    @OneToOne
    @JoinColumn(name = "payment_id",referencedColumnName = "id")
    private Payment payment;

    @OneToMany(mappedBy = "orderDt")
    private List<OrderDetail> orderDetails;
}
