package com.juno.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String fullName;
    private String phone;
    private String email;
    private String city;
    private String district;
    private String wards;
    private String addressDetail;
    private String description;
    private String methodTransport;
    private String methodPay;
    private Long total;
    private List<Long> listPro;
}

