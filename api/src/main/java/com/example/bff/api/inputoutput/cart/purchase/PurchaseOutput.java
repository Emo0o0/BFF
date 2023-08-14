package com.example.bff.api.inputoutput.cart.purchase;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOutput implements OperationResult {

    private boolean success;
}
