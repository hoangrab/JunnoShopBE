package com.juno.controller;

import com.juno.entity.Product;
//import com.juno.model.ProductElasticModel;
//import com.juno.service.impl.ProductElasticService;
import com.juno.model.ProductElasticModel;
import com.juno.service.impl.ProductElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/es/")
public class ProductElasticController {
    @Autowired
    private ProductElasticService productElasticService;

    @GetMapping("products")
    public Iterable<ProductElasticModel> getProducts(@RequestParam(value = "name",defaultValue = "zxzxzxzxz",required = false) String name) {
        try {
            return productElasticService.getAllByName(name);
        }catch (Exception e) {
            return null;
        }
    }
}
