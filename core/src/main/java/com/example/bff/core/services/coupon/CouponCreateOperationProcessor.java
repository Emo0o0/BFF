package com.example.bff.core.services.coupon;

import com.example.bff.api.inputoutput.coupon.create.CouponCreateOperation;
import com.example.bff.api.inputoutput.coupon.create.CreateCouponInput;
import com.example.bff.api.inputoutput.coupon.create.CreateCouponOutput;
import com.example.bff.persistence.entities.Coupon;
import com.example.bff.persistence.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponCreateOperationProcessor implements CouponCreateOperation {

    private final CouponRepository couponRepository;

    @Override
    public CreateCouponOutput process(CreateCouponInput input) {

        Coupon coupon = Coupon.builder()
                .title(input.getTitle())
                .discount(Double.parseDouble(input.getDiscount()))
                .build();

        couponRepository.save(coupon);

        CreateCouponOutput output = CreateCouponOutput.builder()
                .id(coupon.getId().toString())
                .title(coupon.getTitle())
                .discount(coupon.getDiscount().toString())
                .build();

        return output;
    }
}
