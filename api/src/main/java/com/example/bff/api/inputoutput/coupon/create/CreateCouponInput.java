package com.example.bff.api.inputoutput.coupon.create;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCouponInput implements OperationInput {

    private String title;
    private String discount;
}
