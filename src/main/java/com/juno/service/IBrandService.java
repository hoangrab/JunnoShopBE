package com.juno.service;

import com.juno.DTO.BrandDTO;
import com.juno.entity.Brand;

import java.io.IOException;
import java.util.List;

public interface IBrandService {
    List<Brand> getAllBrands();
    Brand getBrandById(Long id);
    void saveBrand(BrandDTO brandDTO) throws IOException;
    void updateBrand(BrandDTO brandDTO, Long id) throws IOException;
    void deleteBrandById(Long id) throws IOException;
}
