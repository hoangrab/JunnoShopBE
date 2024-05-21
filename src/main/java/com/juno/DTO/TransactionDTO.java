package com.juno.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
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
    private Long moneyReduced;
}
