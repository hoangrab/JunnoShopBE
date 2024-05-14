package com.juno.repository;

import com.juno.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ProductItemRepo extends JpaRepository<ProductItem, Long> {
}
