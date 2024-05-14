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
@Table(name = "size_option")
public class SizeOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    private String sortOrder;

    @OneToMany(mappedBy = "size_option")
    private List<ProductItem> productItems;

    public SizeOption(String name) {
        this.name = name;
    }
}
