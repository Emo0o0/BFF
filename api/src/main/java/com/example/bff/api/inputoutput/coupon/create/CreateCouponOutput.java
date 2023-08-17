package com.example.bff.api.inputoutput.coupon.create;

import com.example.bff.api.base.OperationResult;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponOutput implements OperationResult {
    private String id;
    private String title;
    private String discount;
}
