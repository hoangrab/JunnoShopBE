package com.juno.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUser;
    private String listProductItem;
    private String fullName;
    private String email;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String addressDetail;
    private String note;
    private Long money;
    private Long money_reduced;
}
