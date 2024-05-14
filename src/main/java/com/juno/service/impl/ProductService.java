package com.juno.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juno.DTO.FieldProduct;
import com.juno.DTO.ProductDTO;
import com.juno.entity.*;
import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.exception.ResourceNotFoundException;
import com.juno.model.ProductModel;
import com.juno.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductItemRepo productItemRepo;
    private final ProductImageRepo productImageRepo;
    private final CloudinaryService cloudinaryService;
    private final CategoryRepository categoryRepository;
    private final ColorRepo colorRepo;
    private final SizeOptionRepo sizeOptionRepo;

    public List<ProductModel> getAllProducts() {
        return productRepo.findAll().stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    public List<ProductModel> searchProducts(String keyword) {
        return null;
    }

    @Transactional
    public void saveProduct(ProductDTO productDTO) throws IOException {
        if(productRepo.findByName(productDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExitsException("Name of product already exists");
        }
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(category);
        ObjectMapper objectMapper = new ObjectMapper();
        productDTO.getFieldProducts().forEach(e -> {
            FieldProduct fieldProduct = objectMapper.convertValue(e, FieldProduct.class);
            Color color = colorRepo.findByName(fieldProduct.getColor())
                    .orElse(colorRepo.save(new Color(fieldProduct.getColor())));
            SizeOption sizeOption = sizeOptionRepo.findByName(fieldProduct.getSize())
                    .orElse(sizeOptionRepo.save(new SizeOption(fieldProduct.getSize())));
            ProductItem productItem = new ProductItem();
            productItem.setProductIt(product);
            productItem.setColor(color);
            productItem.setSize_option(sizeOption);
            productItem.setQuantity(fieldProduct.getQuantity());
            productItemRepo.save(productItem);
        });
        if(productDTO.getImage().length > 0) {
            List<ProductImage> productImages = new ArrayList<>();
            for(int i = 0; i < productDTO.getImage().length; i++) {
                Map data = cloudinaryService.upload(productDTO.getImage()[i]);
                productImages.add(productImageRepo.save(new ProductImage(data.get("url").toString(),
                        data.get("public_id").toString())));
            }
            product.setProductImages(productImages);
        }
        productRepo.save(product);
    }

    @Transactional
    public void updateProduct(ProductDTO productDTO) {
        productRepo.save(convertDTOtoEntity(productDTO));
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    public Product convertDTOtoEntity(ProductDTO productDTO) {
        return new Product();
    }

    public ProductModel convertEntityToModel(Product product) {
        ProductModel productModel = new ProductModel();
        productModel.setId(product.getId());
        productModel.setName(product.getName());
        productModel.setDescription(product.getDescription());
        return productModel;
    }
}
