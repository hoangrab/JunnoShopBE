package com.juno.repository;

import com.juno.entity.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepo extends JpaRepository<Coupons, Long> {
    Optional<Coupons> findByCode(String couponCode);
}
