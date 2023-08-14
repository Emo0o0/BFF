package com.example.bff.api.inputoutput.cart.getAllItemsFromCart;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllItemsFromCartListOutput implements OperationInput {

    private List<GetAllItemsFromCartOutput> itemsList;
    private BigDecimal totalPrice;

}
