package com.example.bff.core.services.cart;

import com.example.bff.api.inputoutput.cart.returnitems.ReturnItemsInput;
import com.example.bff.api.inputoutput.cart.returnitems.ReturnItemsListInput;
import com.example.bff.api.inputoutput.cart.returnitems.ReturnItemsOperation;
import com.example.bff.api.inputoutput.cart.returnitems.ReturnItemsOutput;
import com.example.bff.core.exceptions.UserNotFoundException;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import com.example.zoostorestorage.api.inputoutput.orderreturnitems.ReturnItemInput;
import com.example.zoostorestorage.api.inputoutput.orderreturnitems.ReturnItemListInput;
import com.example.zoostorestorage.api.inputoutput.orderreturnitems.ReturnItemOutput;
import com.example.zoostorestorage.restexport.ZooStorageRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReturnItemsOperationProcessor implements ReturnItemsOperation {

    private final ZooStorageRestClient zooStorageRestClient;
    private final UserRepository userRepository;

    @Override
    public ReturnItemsOutput process(ReturnItemsListInput input) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(
                authentication.getName()).orElseThrow(() -> new UserNotFoundException("User does not exist"));

        ReturnItemListInput listInput = ReturnItemListInput.builder()
                .userId(user.getId().toString())
                .orderRecordId(input.getOrderRecordId())
                .itemsForReturn(input.getReturnItems().stream()
                        .map(returnItem -> ReturnItemInput.builder()
                                .itemId(returnItem.getItemId())
                                .quantity(returnItem.getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        ReturnItemOutput output = zooStorageRestClient.returnItems(listInput);

        return ReturnItemsOutput.builder()
                .success(output.getSuccess())
                .build();

    }
}
