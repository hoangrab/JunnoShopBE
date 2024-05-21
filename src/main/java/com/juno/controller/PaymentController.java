package com.juno.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juno.DTO.TestDTO;
import com.juno.DTO.TransactionDTO;
import com.juno.service.impl.OrderService;
import com.juno.service.impl.PaymentService;
import com.juno.service.impl.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Base64;

@RestController
@RequestMapping("api/v1/payment/")
@RequiredArgsConstructor
public class PaymentController {
    private final SimpMessagingTemplate messagingTemplate;
    private final PaymentService paymentService;
    private final TransactionService transactionService;
    private final OrderService orderService;

    @GetMapping("create")
    public RedirectView createPayment(@RequestParam("money") String money,
                                      @RequestParam("content") String content) {

        try {
            Long id = -1L;
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytesGiaiMa = Base64.getDecoder().decode(content);
            String giaiMaBase64 = new String(bytesGiaiMa);
            TransactionDTO transactionDTO = objectMapper.readValue(giaiMaBase64, TransactionDTO.class);
            id = transactionService.createTransaction(transactionDTO);
            String querry = paymentService.createPayment(money,id + "");
            return new RedirectView(querry);
        }catch (Exception e) {
            return new RedirectView("error");
        }
    }


    // Tra ve client 1 thông báo thanh toan thanh cong = websocket
    @GetMapping("payInfo")
    public String payment(@RequestParam("vnp_OrderInfo") Long id) {
        orderService.createOrderOnline(id);
        messagingTemplate.convertAndSend("/topic/notifications", "thanh toan ok");
        return "Qúy khách đã thanh toán thành công";
    }


}

