package com.juno.controller;

import com.juno.DTO.OrderDTO;
import com.juno.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class OrderController {

    @GetMapping("orders")
    public ResponseEntity<?> getOrders() {
        return null;
    }

    @PostMapping("order")
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        try {
            return null;
        }catch (Exception e) {
            return null;
        }
    }

    @PutMapping("order/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable int id, @RequestBody Order order) {
        return null;
    }

    @DeleteMapping("order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable int id) {
        return null;
    }
}
