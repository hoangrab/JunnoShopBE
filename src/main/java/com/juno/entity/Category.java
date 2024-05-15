package com.juno.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    private String description;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime time_updated;

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime time_created;

    @OneToOne
    @JsonIgnore
    @JoinColumn(referencedColumnName = "id",name = "parent_id")
    private Category parent;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Product> products;

}
