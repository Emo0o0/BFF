package com.example.bff.api.inputoutput.coupon.getbytitle;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCouponByTitleOutput implements OperationResult {

    private String id;
    private String title;
    private String discount;


}
