package com.example.bff.api.inputoutput.getItemsByTag;

import com.example.bff.api.base.OperationResult;
import com.example.zoostore.api.operations.inputOutput.vendor.getVendorById.MultimediaToDtoSetMap;
import com.example.zoostore.api.operations.inputOutput.vendor.getVendorById.TagsToDtoSetMap;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetItemsByTagOutput implements OperationResult {

    private String id;
    private String title;
    private String description;
    private Boolean archived;
    private String vendorID;
    private Set<MultimediaToDtoSetMap> multimedia;
    private Set<TagsToDtoSetMap> tags;
    private String quantity;
    private String price;

}
