package com.example.bff.api.inputoutput.getItemsByTag;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetItemsByTagListOutput implements OperationResult {

    private Set<GetItemsByTagOutput> items;
}
