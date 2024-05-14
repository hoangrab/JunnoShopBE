//package com.juno.repository;
//
//import com.juno.model.ProductElasticModel;
//import org.springframework.data.elasticsearch.annotations.Query;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface ProductElasticSearchRepo extends ElasticsearchRepository<ProductElasticModel,Long> {
//    public interface ProductRepo extends ElasticsearchRepository<ProductElasticModel, Long> {
//        @Query("{\"query_string\": {\"query\": \"*?0*\", \"fields\": [\"name\"]}}")
//        Iterable<ProductElasticModel> findByName(String name);
//    }
//}
