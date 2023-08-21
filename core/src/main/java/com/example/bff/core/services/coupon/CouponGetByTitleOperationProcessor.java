package com.example.bff.core.services.coupon;

import com.example.bff.api.inputoutput.coupon.getbytitle.CouponGetByTitleOperation;
import com.example.bff.api.inputoutput.coupon.getbytitle.GetCouponByTitleInput;
import com.example.bff.api.inputoutput.coupon.getbytitle.GetCouponByTitleOutput;
import com.example.bff.core.exceptions.CouponNotFoundException;
import com.example.bff.persistence.entities.Coupon;
import com.example.bff.persistence.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponGetByTitleOperationProcessor implements CouponGetByTitleOperation {

    private final CouponRepository couponRepository;

    @Override
    public GetCouponByTitleOutput process(GetCouponByTitleInput input) {

        Coupon coupon = couponRepository.findByTitle(input.getTitle())
                .orElseThrow(() -> new CouponNotFoundException("Could not find coupon"));

        GetCouponByTitleOutput output = GetCouponByTitleOutput.builder()
                .id(coupon.getId().toString())
                .title(coupon.getTitle())
                .discount(coupon.getDiscount().toString())
                .build();

        return output;
    }
}
