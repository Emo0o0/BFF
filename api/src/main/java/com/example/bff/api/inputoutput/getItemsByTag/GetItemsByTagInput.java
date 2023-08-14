package com.example.bff.api.inputoutput.getItemsByTag;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetItemsByTagInput implements OperationInput {

    private String tagTitle;
    private Integer itemsPerPage;
    private Integer currentPage;
}
