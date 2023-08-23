package com.example.bff.api.inputoutput.cart.returnitems;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnItemsInput implements OperationInput {

    private String itemId;
    private int quantity;

}
