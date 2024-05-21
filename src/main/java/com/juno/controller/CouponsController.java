package com.juno.controller;

import com.juno.DTO.CouponDTO;
import com.juno.entity.Coupons;
import com.juno.service.impl.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class CouponsController {
    private final CouponService couponService;

    @GetMapping("coupons")
    public ResponseEntity<?> getAllCoupons() {
        try {
            List<Coupons> list = couponService.getAllCoupons();
            return ResponseEntity.ok(list);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("coupon")
    public ResponseEntity<?> getCouponByCode(@RequestParam(value = "code") String code) {
        try {
            Coupons coupon = couponService.getCouponByCode(code);
            return ResponseEntity.ok(coupon);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("coupon/{id}")
    public ResponseEntity<?> getCouponById(@PathVariable Long id) {
        try {
            Coupons coupons = couponService.getCouponById(id);
            return ResponseEntity.ok(coupons);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("coupon")
    public ResponseEntity<?> creatCoupon(@ModelAttribute CouponDTO couponDTO) {
        try {
            couponService.createCoupon(couponDTO);
            return ResponseEntity.ok().build();
        }catch (Exception ex ) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("coupon/{id}")
    public ResponseEntity<?> updateCoupon(@PathVariable Long id, @ModelAttribute CouponDTO couponDTO) {
        try {
            couponService.updateCoupon(id, couponDTO);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("coupon/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) {
        try {
            couponService.deleteCoupon(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
