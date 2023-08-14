package com.example.bff.api.inputoutput.getItemById;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetItemInput implements OperationInput {

    @NotBlank(message = "Item id cannot be empty")
    private String itemId;
}
