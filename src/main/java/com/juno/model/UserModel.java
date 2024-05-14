package com.juno.model;

import com.juno.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String avatar;
    private String fullName;
    private String email;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String addressDetail;
    private boolean enabled;
    private Role role;

    public UserModel(Long id,String avatar, String fullName, String email, String phone, String city, String district, String ward, String addressDetail,boolean enabled, Role role) {
        this.id = id;
        this.avatar = avatar;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.addressDetail = addressDetail;
        this.enabled = enabled;
        this.role = role;
    }
}
