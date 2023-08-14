package com.example.bff.api.inputoutput.cart.changeItemQuantity;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeItemQuantityOutput implements OperationResult {

    private String userId;
    private String itemId;
    private int quantity;
}
