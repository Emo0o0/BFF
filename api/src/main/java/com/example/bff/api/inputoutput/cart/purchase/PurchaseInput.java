package com.example.bff.api.inputoutput.cart.purchase;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseInput implements OperationInput {

    private String coupon;

}
