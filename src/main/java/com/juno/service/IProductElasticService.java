package com.juno.service;

import com.juno.model.ProductElasticModel;

public interface IProductElasticService {
    Iterable<ProductElasticModel> getAllByName(String name);
    Iterable<ProductElasticModel> getAll();
    void save(ProductElasticModel productElasticModel);
    void delete(Long id);
}
