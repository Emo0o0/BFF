package com.example.bff.core.services;

import com.example.bff.api.inputoutput.getItemById.GetItemInput;
import com.example.bff.api.inputoutput.getItemById.GetItemOutput;
import com.example.bff.api.inputoutput.getItemById.ItemGetByIdOperation;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.zoostore.api.operations.inputOutput.item.getItemById.GetItemByIdOutput;
import com.example.zoostore.restexport.ZooStoreRestClient;
import com.example.zoostorestorage.api.inputOutput.getItemFromStorage.GetItemFromStorageOutput;
import com.example.zoostorestorage.restexport.ZooStorageRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemGetByIdOperationProcessor implements ItemGetByIdOperation {

    private final ZooStoreRestClient zooStoreRestClient;
    private final ZooStorageRestClient zooStorageRestClient;

    @Override
    public GetItemOutput process(GetItemInput input) {

        try {
            zooStoreRestClient.getItemById(input.getItemId());
        } catch (Exception e) {
            throw new ItemNotFoundException("Item with this id does not exist in the zoo store database");
        }

        try {
            zooStorageRestClient.getItemFromStorage(input.getItemId());
        } catch (Exception e) {
            throw new ItemNotFoundException("Item with this id does not exist in the zoo storage database");
        }

        GetItemByIdOutput storeItem = zooStoreRestClient.getItemById(input.getItemId());
        GetItemFromStorageOutput storageItem = zooStorageRestClient.getItemFromStorage(input.getItemId());

        GetItemOutput output = GetItemOutput.builder()
                .id(storeItem.getId())
                .title(storeItem.getTitle())
                .description(storeItem.getDescription())
                .archived(String.valueOf(storeItem.isArchived()))
                .vendorID(storeItem.getVendorID())
                .multimedia(storeItem.getMultimedia())
                .tags(storeItem.getTags())
                .quantity(storageItem.getQuantity())
                .price(storageItem.getPrice())
                .build();

        return output;
    }
}
