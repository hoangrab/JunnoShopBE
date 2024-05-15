package com.juno.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemModel {
    private int id;
    private String color;
    private String size;
    private int quantity;
}
