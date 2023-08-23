package com.example.bff.api.inputoutput.cart.returnitems;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnItemsOutput implements OperationResult {

    private boolean success;

}
