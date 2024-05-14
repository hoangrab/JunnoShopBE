package com.juno.controller;

import com.juno.service.impl.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("api/v1/payment/")
@RequiredArgsConstructor
public class PaymentController {
    private SimpMessagingTemplate messagingTemplate;
    private final PaymentService paymentService;

    @GetMapping("create")
    public RedirectView createPayment(@RequestParam("money") String money,
                                      @RequestParam("content") String content) {
        try {
            String querry = paymentService.createPayment(money,content);
            return new RedirectView(querry);
        }catch (Exception e) {
            return new RedirectView("error");
        }
    }


    // Tra ve client 1 thông báo thanh toan thanh cong = websocket
    @GetMapping("payInfo")
    public String payment() {
        messagingTemplate.convertAndSend("/topic/notifications", "thanh toan ok");
        return "Qúy khách đã thanh toán thành công";
    }


}

