package com.juno.repository;

import com.juno.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, String> {
    List<Order> findByUserOrdId(Long customer);
}
