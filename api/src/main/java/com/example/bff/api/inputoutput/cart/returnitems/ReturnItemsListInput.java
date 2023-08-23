package com.example.bff.api.inputoutput.cart.returnitems;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnItemsListInput implements OperationInput {

        private String orderRecordId;
        private List<ReturnItemsInput> returnItems;

}
