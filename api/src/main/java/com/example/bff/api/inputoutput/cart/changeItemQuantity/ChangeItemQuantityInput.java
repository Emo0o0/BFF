package com.example.bff.api.inputoutput.cart.changeItemQuantity;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeItemQuantityInput implements OperationInput {

    private String itemId;
    private int quantity;


}
