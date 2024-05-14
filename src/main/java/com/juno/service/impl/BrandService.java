package com.juno.service.impl;

import com.juno.DTO.BrandDTO;
import com.juno.entity.Brand;
import com.juno.entity.ProductImage;
import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.exception.ResourceNotFoundException;
import com.juno.repository.BrandRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepo brandRepo;
    private final CloudinaryService cloudinaryService;

    public List<Brand> getAllBrands() {
        return brandRepo.findAll();
    }

    public Brand getBrandById(Long id) {
        return brandRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
    }

    @Transactional
    public void saveBrand(BrandDTO brandDTO) throws IOException {
        if(brandRepo.findByName(brandDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExitsException("Name of brand already exists");
        }
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        if(brandDTO.getLogo()!=null) {
            Map data = cloudinaryService.upload(brandDTO.getLogo());
            brand.setLogo(data.get("url").toString());
            brand.setUrl_id(data.get("public_id").toString());
        }
        brandRepo.save(brand);
    }

    @Transactional
    public void updateBrand(BrandDTO brandDTO, Long id) throws IOException {
        Brand brand = brandRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
        if(!brand.getName().equals(brandDTO.getName()) && brandRepo.findByName(brandDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExitsException("Name of brand already exists");
        }
        brand.setName(brandDTO.getName());
        if(brandDTO.getLogo()!=null) {
            cloudinaryService.delete(brand.getUrl_id());
            Map data = cloudinaryService.upload(brandDTO.getLogo());
            brand.setLogo(data.get("url").toString());
            brand.setUrl_id(data.get("public_id").toString());
        }
        brandRepo.save(brand);
    }

    @Transactional
    public void deleteBrandById(Long id) throws IOException {
        Brand brand = brandRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
        String idUrl = brand.getUrl_id();
        brandRepo.deleteById(id);
        cloudinaryService.delete(idUrl);
    }


}
