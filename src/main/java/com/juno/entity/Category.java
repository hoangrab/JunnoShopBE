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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    private String image;

    private String description;

    @OneToOne
    @JoinColumn(referencedColumnName = "id",name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}
