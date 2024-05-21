package com.juno.service;

import com.juno.DTO.CouponDTO;
import com.juno.entity.Coupons;

import java.util.List;

public interface ICouponService {
    List<Coupons> getAllCoupons();
    Coupons getCouponByCode(String code);
    Coupons getCouponById(Long id);
    void createCoupon(CouponDTO couponDTO);
    void updateCoupon(Long id, CouponDTO couponDTO);
    void deleteCoupon(Long id);
}
