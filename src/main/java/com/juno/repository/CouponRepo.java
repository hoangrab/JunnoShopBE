package com.juno.repository;

import com.juno.entity.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

public interface CouponRepo extends JpaRepository<Coupons, Long> {
    Optional<Coupons> findByCode(String couponCode);

    Optional<Coupons> findByCodeAndDateStartLessThanAndDateEndGreaterThan(String couponCode, Date datenow, Date date);
}
