package com.example.bff.core.services;

import com.example.bff.api.inputoutput.getItemsByTag.GetItemsByTagInput;
import com.example.bff.api.inputoutput.getItemsByTag.GetItemsByTagListOutput;
import com.example.bff.api.inputoutput.getItemsByTag.GetItemsByTagOutput;
import com.example.bff.api.inputoutput.getItemsByTag.ItemsGetByTagOperation;
import com.example.zoostore.api.operations.inputoutput.item.getallbytag.GetAllItemsByTagListOutput;
import com.example.zoostore.api.operations.inputoutput.item.getallbytag.GetAllItemsByTagOutput;
import com.example.zoostore.restexport.ZooStoreRestClient;
import com.example.zoostorestorage.api.inputoutput.item.getfromstorage.GetItemFromStorageOutput;
import com.example.zoostorestorage.restexport.ZooStorageRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ItemsGetByTagOperationProcessor implements ItemsGetByTagOperation {

    private final ZooStoreRestClient zooStoreRestClient;
    private final ZooStorageRestClient zooStorageRestClient;

    @Override
    public GetItemsByTagListOutput process(GetItemsByTagInput input) {


        GetAllItemsByTagListOutput items = zooStoreRestClient.getItemsByTag(input.getTagTitle(), input.getItemsPerPage(), input.getCurrentPage());
        GetItemsByTagListOutput outputList = GetItemsByTagListOutput.builder().items(new HashSet<>()).build();


        for (GetAllItemsByTagOutput item : items.getItems()) {

            GetItemFromStorageOutput storageItem = zooStorageRestClient.getItemFromStorage(item.getId());


            GetItemsByTagOutput output = GetItemsByTagOutput.builder()
                    .id(item.getId().toString())
                    .title(item.getTitle())
                    .description(item.getDescription())
                    .archived(Boolean.valueOf(item.getArchived()))
                    .vendorID(item.getVendorID())
                    .multimedia(item.getMultimedia())
                    .tags(item.getTags())
                    .quantity(storageItem.getQuantity())
                    .price(storageItem.getPrice())
                    .build();

            outputList.getItems().add(output);
        }
        return outputList;
    }
}
