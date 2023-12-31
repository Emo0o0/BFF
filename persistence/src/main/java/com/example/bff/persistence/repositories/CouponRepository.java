package com.example.bff.persistence.repositories;

import com.example.bff.persistence.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {


    Optional<Coupon> findByTitle(String title);


}
