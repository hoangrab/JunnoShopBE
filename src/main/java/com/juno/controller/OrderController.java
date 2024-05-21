package com.juno.controller;

import com.juno.DTO.OrderDTO;
import com.juno.DTO.TransactionDTO;
import com.juno.entity.Order;
import com.juno.model.OrderModel;
import com.juno.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("orders")
    public ResponseEntity<?> getOrders(@RequestParam(value = "idUser",defaultValue = "0") Long idUser) {
        try {
            List<OrderModel> list = orderService.getAllOrders(idUser);
            return ResponseEntity.ok(list);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("order/{id}")
    public ResponseEntity<?> getOrder(@PathVariable String id) {
        try {
            OrderModel orderModel = orderService.getOrderById(id);
            return ResponseEntity.ok(orderModel);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("order")
    public ResponseEntity<?> addOrder(@RequestBody TransactionDTO orderDTO) {
        try {
            orderService.creteOrderOff(orderDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("order/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable String id, @RequestParam("status") String status) {
        try {
            orderService.updateOrder(status,id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
