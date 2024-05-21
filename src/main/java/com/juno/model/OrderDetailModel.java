package com.juno.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailModel {
    private Long id;
    private String name;
    private int quantity;
    private String image;
    private String size;
    private String color;
    private Long price;
}