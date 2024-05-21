package com.juno.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
    private String id;
    private String fullName;
    private String email;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String addressDetail;
    private String note;
    private String status;
    private boolean paid;
    private String orderDate;
    private Long userId;
    private Long money;
    private Long moneyReduced;
    private List<OrderDetailModel> orderDetails;
}
