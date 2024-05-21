package com.juno.entity;

import com.juno.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String fullName;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String city;

    @Column
    private String district;

    @Column
    private String ward;

    @Column
    private String addressDetail;

    @Column
    private String note;

    @Column
    private Long money;

    @Column
    private Long money_reduced;

    @Enumerated(EnumType.STRING)
    private Status status;

    private boolean paid;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User userOrd;

    @OneToMany(mappedBy = "orderDt")
    private List<OrderDetail> orderDetails;
}
