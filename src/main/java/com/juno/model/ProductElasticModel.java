//package com.juno.model;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//@Document(indexName = "productEs",createIndex = true)
//public class ProductElasticModel {
//    @Id
//    private long id;
//
//    @Field(type = FieldType.Text,name = "name")
//    private String name;
//
//    @Field(type = FieldType.Integer,name = "quantity")
//    private int quantity;
//}
