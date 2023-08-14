package com.example.bff.api.inputoutput.cart.removeItemFromCart;

import com.example.bff.api.base.OperationInput;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemoveItemFromCartInput implements OperationInput {

    private String itemId;
    private String userId;

}
