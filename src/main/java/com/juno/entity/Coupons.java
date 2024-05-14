package com.juno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupons")
public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,nullable = false,unique = true)
    private String code;

    @Column(nullable = false)
    private int percent;

    @Column
    private Date dateStart;

    @Column
    private Date dateEnd;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @CreationTimestamp
    private LocalDateTime createTime;
}
