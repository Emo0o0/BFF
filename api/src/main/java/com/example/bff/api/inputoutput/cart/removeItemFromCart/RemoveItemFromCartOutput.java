package com.example.bff.api.inputoutput.cart.removeItemFromCart;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemoveItemFromCartOutput implements OperationResult {

    private boolean success;
}
