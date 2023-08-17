package com.example.bff.rest.controllers;

import com.example.bff.api.inputoutput.coupon.create.CouponCreateOperation;
import com.example.bff.api.inputoutput.coupon.create.CreateCouponInput;
import com.example.bff.api.inputoutput.coupon.create.CreateCouponOutput;
import com.example.bff.api.inputoutput.coupon.getbytitle.CouponGetByTitleOperation;
import com.example.bff.api.inputoutput.coupon.getbytitle.GetCouponByTitleInput;
import com.example.bff.api.inputoutput.coupon.getbytitle.GetCouponByTitleOutput;
import com.example.bff.core.services.coupon.CouponCreateOperationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final CouponCreateOperation couponCreateOperation;
    private final CouponGetByTitleOperation couponGetByTitleOperation;

    @PostMapping
    public ResponseEntity<CreateCouponOutput> createCoupon(CreateCouponInput input){
        return ResponseEntity.status(201).body(couponCreateOperation.process(input));
    }

    @GetMapping
    public ResponseEntity<GetCouponByTitleOutput> getCouponByTitle(GetCouponByTitleInput input){
        return ResponseEntity.status(200).body(couponGetByTitleOperation.process(input));
    }


}
