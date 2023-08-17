package com.example.bff.api.inputoutput.coupon.getbytitle;

import com.example.bff.api.base.OperationInput;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCouponByTitleInput implements OperationInput {

    private String title;
}
