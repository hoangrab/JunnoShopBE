package com.juno.controller;

import com.juno.request.LoginRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("test")
    public Map<String,Object> testnha() {
        Map<String,Object> map = new HashMap<>();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("ga");
        loginRequest.setPassword("vip");
        loginRequest.setToken("haizz");
        map.put("user",loginRequest);
        map.put("jwt","vipnhatjwt");
        return map;
    }

}
