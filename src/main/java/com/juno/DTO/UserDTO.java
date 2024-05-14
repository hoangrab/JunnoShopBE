package com.juno.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String fullName;
    private String gmail;
    private String password;
    private String city;
    private String district;
    private String ward;
    private String addressDetail;
    private String phone;
    private boolean enabled;
    private MultipartFile avatar;
}
