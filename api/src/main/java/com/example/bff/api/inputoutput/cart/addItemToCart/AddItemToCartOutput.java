package com.example.bff.api.inputoutput.cart.addItemToCart;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddItemToCartOutput implements OperationResult {

    private String itemId;
    private String userId;
    private int quantity;

}
