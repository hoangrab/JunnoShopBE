package com.juno.service.impl;

import com.juno.DTO.CouponDTO;
import com.juno.entity.Coupons;
import com.juno.exception.ResourceAlreadyExitsException;
import com.juno.exception.ResourceNotFoundException;
import com.juno.repository.CouponRepo;
import com.juno.service.ICouponService;
import com.juno.utils.ConvertTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService implements ICouponService {
    private final CouponRepo couponRepo;

    public List<Coupons> getAllCoupons() {
        return couponRepo.findAll();
    }

    public Coupons getCouponByCode(String code) {
        Date date = new Date();
        System.out.println(date);
        Optional<Coupons> coupon = couponRepo.findByCodeAndDateStartLessThanAndDateEndGreaterThan(code,date,date);
        System.out.println(coupon.get());
        return coupon.get();
    }

    public Coupons getCouponById(Long id) {
        return couponRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Coupon Not Found"));
    }

    @Transactional
    public void createCoupon(CouponDTO couponDTO) {
        if(couponRepo.findByCode(couponDTO.getCode()).isPresent()) {
            throw new ResourceAlreadyExitsException("Coupon already exists");
        }
        Coupons coupons = new Coupons();
        coupons.setCode(couponDTO.getCode());
        coupons.setPercent(couponDTO.getPercent());
        coupons.setDateStart(ConvertTime.stringToDate(couponDTO.getDateStart()));
        coupons.setDateEnd(ConvertTime.stringToDate(couponDTO.getDateEnd()));
        couponRepo.save(coupons);
    }

    @Transactional
    public void updateCoupon(Long id,CouponDTO couponDTO) {
        Coupons coupons = couponRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon Not Found"));
        coupons.setDateStart(ConvertTime.stringToDate(couponDTO.getDateStart()));
        coupons.setDateEnd(ConvertTime.stringToDate(couponDTO.getDateEnd()));
        couponRepo.save(coupons);
    }

    @Transactional
    public void deleteCoupon(Long id) {
        couponRepo.deleteById(id);
    }
}
