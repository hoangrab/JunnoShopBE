//package com.juno.service.impl;
//
//import com.juno.model.ProductElasticModel;
//import com.juno.repository.ProductElasticSearchRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ProductElasticService {
//    @Autowired
//    private ProductElasticSearchRepo productElasticSearchRepo;
//
//    // find all product
//    public Iterable<ProductElasticModel> getAll() {
//        return productElasticSearchRepo.findAll();
//    }
//
//    // update product save luôn cũng được , nó sẽ dựa vào @Id để phân biệt có rồi nó chỉ update
//    public void save(ProductElasticModel productElasticModel) {
//        productElasticSearchRepo.save(productElasticModel);
//    }
//
//
//}
