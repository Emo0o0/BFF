package com.example.bff.api.inputoutput.cart.dropCart;

import com.example.bff.api.base.OperationResult;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DropCartOutput implements OperationResult {

    private boolean success;
}
