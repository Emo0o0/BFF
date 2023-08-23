package com.example.bff.api.inputoutput.cart.changeItemQuantity;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeItemQuantityInput implements OperationInput {

    @NotBlank(message = "Item id cannot be blank")
    private String itemId;
    @Positive(message = "Quantity should be greater than zero")
    private int quantity;


}
