package com.juno.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private Long categoryId;
    private int quantity;
    private Long priceCurrent;
    private Long priceReduced;
    private String flashSale;
    private List<String> fieldProducts;
    private MultipartFile[] image;
    private String description;
}
