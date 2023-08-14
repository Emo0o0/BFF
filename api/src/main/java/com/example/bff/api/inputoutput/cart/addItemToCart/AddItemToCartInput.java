package com.example.bff.api.inputoutput.cart.addItemToCart;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddItemToCartInput implements OperationInput {

    //@NotBlank
    //private String userId;
    @NotBlank
    private String itemId;
    //@Positive
    private String quantity;

}
