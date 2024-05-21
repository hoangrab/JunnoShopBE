package com.juno.service.impl;

import com.juno.DTO.OrderDTO;
import com.juno.DTO.TransactionDTO;
import com.juno.constant.Status;
import com.juno.entity.*;
import com.juno.exception.ResourceNotFoundException;
import com.juno.model.OrderDetailModel;
import com.juno.model.OrderModel;
import com.juno.repository.*;
import com.juno.service.IOrderService;
import com.juno.utils.ConvertTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepo orderRepo;
    private final OrderDetailRepo orderDetailRepo;
    private final TransactionService transactionService;
    private final UserRepo userRepo;
    private final ProductItemRepo productItemRepo;
    private final ProductRepo productRepo;

    public List<OrderModel> getAllOrders(Long idUser) {
        if(idUser == 0) {
            return this.orderRepo.findAll().stream().map(this::convertEntityToModel).collect(Collectors.toList());
        }
        return this.orderRepo.findByUserOrdId(idUser).stream().map(this::convertEntityToModel).collect(Collectors.toList());
    }

    public OrderModel getOrderById(String id) {
        Optional<Order> op = orderRepo.findById(id);
        if(op.isEmpty()) throw new ResourceNotFoundException("Order not found");
        return convertEntityToModel(op.get());
    }

    public void creteOrderOff(TransactionDTO transaction) {
        Order order = new Order();
        User user = userRepo.findById(transaction.getIdUser()).get();
        order.setUserOrd(user);
        order.setFullName(transaction.getFullName());
        order.setEmail(transaction.getEmail());
        order.setPhone(transaction.getPhone());
        order.setCity(transaction.getCity());
        order.setDistrict(transaction.getDistrict());
        order.setWard(transaction.getWard());
        order.setAddressDetail(transaction.getAddressDetail());
        order.setNote(transaction.getNote());
        order.setMoney(transaction.getMoney());
        order.setStatus(Status.CONFIRM);
        order.setMoney_reduced(transaction.getMoneyReduced());
        order.setPaid(false); // false
        orderRepo.save(order);
        List<OrderDetail> list = new ArrayList<>();
        String s = transaction.getListProductItem();
        for (String v : s.split("#")) {
            OrderDetail orderDetail = new OrderDetail();
            ProductItem productItem = productItemRepo.findById(Long.valueOf("" + v.charAt(0))).get();
            orderDetail.setProductItem(productItem);
            orderDetail.setQuantity(Integer.parseInt("" + v.charAt(2)));
            orderDetail.setOrderDt(order);
            orderDetailRepo.save(orderDetail);
            list.add(orderDetail);
        }
        order.setOrderDetails(list);
        orderRepo.save(order);
    }
    public void createOrderOnline(Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        Order order = new Order();
        User user = userRepo.findById(transaction.getIdUser()).get();
        order.setUserOrd(user);
        order.setFullName(transaction.getFullName());
        order.setEmail(transaction.getEmail());
        order.setPhone(transaction.getPhone());
        order.setCity(transaction.getCity());
        order.setDistrict(transaction.getDistrict());
        order.setWard(transaction.getWard());
        order.setAddressDetail(transaction.getAddressDetail());
        order.setNote(transaction.getNote());
        order.setMoney(transaction.getMoney());
        order.setStatus(Status.CONFIRM);
        order.setMoney_reduced(transaction.getMoney_reduced());
        order.setPaid(true);
        orderRepo.save(order);
        List<OrderDetail> list = new ArrayList<>();
        String s = transaction.getListProductItem();
        for (String v : s.split("#")) {
            OrderDetail orderDetail = new OrderDetail();
            ProductItem productItem = productItemRepo.findById(Long.valueOf("" + v.charAt(0))).get();
            orderDetail.setProductItem(productItem);
            orderDetail.setQuantity(Integer.parseInt("" + v.charAt(2)));
            orderDetail.setOrderDt(order);
            orderDetailRepo.save(orderDetail);
            list.add(orderDetail);
       }
        order.setOrderDetails(list);
        orderRepo.save(order);
    }


    public void updateOrder(String status, String id) {
        Status st = Status.valueOf(status);
        Order order = orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(st);
        orderRepo.save(order);
    }

    public OrderModel convertEntityToModel(Order order) {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(order.getId());
        orderModel.setFullName(order.getFullName());
        orderModel.setEmail(order.getEmail());
        orderModel.setPhone(order.getPhone());
        orderModel.setCity(order.getCity());
        orderModel.setDistrict(order.getDistrict());
        orderModel.setWard(order.getWard());
        orderModel.setAddressDetail(order.getAddressDetail());
        orderModel.setNote(order.getNote());
        orderModel.setStatus(order.getStatus().toString());
        orderModel.setPaid(order.isPaid());
        orderModel.setUserId(order.getUserOrd().getId());
        orderModel.setMoneyReduced(order.getMoney_reduced());
        orderModel.setMoney(order.getMoney());
        if(order.getCreatedAt()!=null) {
            orderModel.setOrderDate(ConvertTime.convertLocalDatetime(order.getCreatedAt()));
        }
        List<OrderDetailModel> list = new ArrayList<>();
        orderDetailRepo.findByOrderDtId(order.getId()).forEach(e -> {
            OrderDetailModel orderDetailModel = new OrderDetailModel();
            Product product = productItemRepo.findById(e.getProductItem().getId()).get().getProductIt();
            orderDetailModel.setId(e.getId());
            orderDetailModel.setName(product.getName());
            orderDetailModel.setQuantity(e.getQuantity());
            orderDetailModel.setImage(product.getProductImages().get(0).getImageUrl());
            orderDetailModel.setSize(e.getProductItem().getSize_option().getName());
            orderDetailModel.setColor(e.getProductItem().getColor().getName());
            if(product.getSale_price()==null) orderDetailModel.setPrice(product.getOriginal());
            else orderDetailModel.setPrice(product.getOriginal());
            list.add(orderDetailModel);
        });
        orderModel.setOrderDetails(list);
        return orderModel;
    }
}
