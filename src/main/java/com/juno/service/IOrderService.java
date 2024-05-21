package com.juno.service;


import com.juno.DTO.TransactionDTO;
import com.juno.entity.Order;
import com.juno.model.OrderModel;

import java.util.List;

public interface IOrderService {
    List<OrderModel> getAllOrders(Long idUser);
    OrderModel getOrderById(String id);
    void creteOrderOff(TransactionDTO transaction);
    void createOrderOnline(Long id);
    void updateOrder(String status, String id);
    OrderModel convertEntityToModel(Order order);
}
