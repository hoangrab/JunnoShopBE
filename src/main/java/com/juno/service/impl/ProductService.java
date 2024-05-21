package com.juno.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juno.DTO.FieldProduct;
import com.juno.DTO.ProductDTO;
import com.juno.entity.*;
import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.exception.ResourceNotFoundException;
import com.juno.model.ProductElasticModel;
import com.juno.model.ProductItemModel;
import com.juno.model.ProductModel;
import com.juno.model.ProductReviewModel;
import com.juno.repository.*;
import com.juno.utils.SpecificationsBuilder;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private final ProductReviewService productReviewService;
    private final ProductElasticService productElasticService;
    private final ProductElasticSearchRepo productElasticSearchRepo;

    public List<ProductModel> getAllProducts() {
        return productRepo.findAll().stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    public ProductModel getProductById(Long id) {
        return convertEntityToModel(productRepo.findById(id).get());
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
        product.setFlash_sale(productDTO.getFlashSale().equals("true"));
        product.setSale_price(productDTO.getPriceReduced());
        product.setOriginal(productDTO.getPriceCurrent());
        product.setDescription(productDTO.getDescription());
        productRepo.save(product);
        ObjectMapper objectMapper = new ObjectMapper();
        productDTO.getFieldProducts().forEach(e -> {
            FieldProduct fieldProduct = null;
            try {
                fieldProduct = objectMapper.readValue(e, FieldProduct.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
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
                productImages.add(productImageRepo.save(
                        new ProductImage(data.get("url").toString(),data.get("public_id").toString(),product)));
            }
            product.setProductImages(productImages);
        }
        productRepo.save(product);
        ProductElasticModel productElasticModel = new ProductElasticModel();
        productElasticModel.setId(product.getId());
        productElasticModel.setName(product.getName());
        productElasticSearchRepo.save(productElasticModel);
    }

    @Transactional
    public void updateProduct(ProductDTO productDTO, Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(productDTO.getName());
        productRepo.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepo.deleteById(id);
        productElasticSearchRepo.deleteById(id);
    }

    public List<ProductModel> searchProducts(Long idCate,String sortName, String sortPrice) {
        Specification<Product> specification1 = (root, query, builder) -> {
            return idCate != 0 ?builder.equal(root.get("category").get("id"), idCate) : null;
//            return builder.or(predicates.toArray(new Predicate[0]));
        };
        Sort sort = null;
        if(StringUtils.isNotEmpty(sortName)) {
            sort = Sort.by(sortName.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "name");
        }
        if(StringUtils.isNotEmpty(sortPrice)) {
            sort = Sort.by(sortPrice.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,"original").descending();
        }
        if(sort != null) {
            return productRepo.findAll(specification1,sort).stream()
                    .map(this::convertEntityToModel)
                    .collect(Collectors.toList());
        }
        return productRepo.findAll(specification1).stream()
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }

    public Product convertDTOtoEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        return product;
    }

    public ProductModel convertEntityToModel(Product product) {
        ProductModel productModel = new ProductModel();
        productModel.setId(product.getId());
        productModel.setName(product.getName());
        productModel.setDescription(product.getDescription());
        productModel.setFlashSale(product.isFlash_sale());
        productModel.setOriginalPrice(product.getOriginal());
        productModel.setSalePrice(product.getSale_price());
        List<ProductItemModel> listProductItemModel = new ArrayList<>();
        product.getProductItems().forEach(e -> {
            ProductItemModel productItemModel = new ProductItemModel();
            productItemModel.setId(e.getId());
            productItemModel.setSize(e.getSize_option().getName());
            productItemModel.setColor(e.getColor().getName());
            productItemModel.setQuantity(e.getQuantity());
            listProductItemModel.add(productItemModel);
        });
        List<ProductReviewModel> list = productReviewService.findAll(product.getId());
        productModel.setProductReviews(list);
        productModel.setCategory(product.getCategory());
        productModel.setProductItems(listProductItemModel);
        productModel.setProductImages(product.getProductImages());
        return productModel;
    }
}
