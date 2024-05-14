package com.juno.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fullName;

    @Column(length = 128)
    private String avatar;

    @Column(length = 64)
    private String url_id;

    @Column(nullable = false, unique = true)
    private String gmail;

    @Column(nullable = false)
    private String password;

    @Column
    private String phone;

    @Column(length = 64)
    private String city;

    @Column(length = 64)
    private String district;

    @Column(length = 64)
    private String ward;

    @Column(length = 128)
    private String addressDetail;

    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "userOrd")
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    private Wishlist wishlist;
}
