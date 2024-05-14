package com.juno.controller;

import com.juno.entity.Product;
//import com.juno.model.ProductElasticModel;
//import com.juno.service.impl.ProductElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/es/")
public class ProductElasticController {
//    @Autowired
//    private ProductElasticService productElasticService;
//
//    @GetMapping("products")
//    public Iterable<ProductElasticModel> getProducts() {
//        try {
//            return productElasticService.getAll();
//        }catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
