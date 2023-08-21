package com.example.bff.core.services.cart;

import com.example.bff.api.inputoutput.cart.getAllItemsFromCart.CartGetAllItemsOperation;
import com.example.bff.api.inputoutput.cart.getAllItemsFromCart.GetAllItemsFromCartInput;
import com.example.bff.api.inputoutput.cart.getAllItemsFromCart.GetAllItemsFromCartListOutput;
import com.example.bff.api.inputoutput.cart.getAllItemsFromCart.GetAllItemsFromCartOutput;
import com.example.bff.core.exceptions.CartItemNotFoundException;
import com.example.bff.core.exceptions.NoItemsInCartException;
import com.example.bff.core.exceptions.UserNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.UserRepository;
import com.example.zoostore.api.operations.inputoutput.item.getlist.GetItemsListInput;
import com.example.zoostore.api.operations.inputoutput.item.getlist.GetItemsListOutput;
import com.example.zoostore.api.operations.inputoutput.vendor.getbyid.ItemsToDtoSetMap;
import com.example.zoostore.persistence.entities.Item;
import com.example.zoostore.restexport.ZooStoreRestClient;
import com.example.zoostorestorage.restexport.ZooStorageRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartGetAllItemsOperationProcessor implements CartGetAllItemsOperation {

    private final UserRepository userRepository;
    private final ZooStoreRestClient zooStoreRestClient;

    @Override
    public GetAllItemsFromCartListOutput process(GetAllItemsFromCartInput getAllItemsFromCartInput) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(
                authentication.getName()).orElseThrow(() -> new UserNotFoundException("User does not exist"));

        GetAllItemsFromCartListOutput outputList = GetAllItemsFromCartListOutput
                .builder()
                .itemsList(new ArrayList<>())
                .build();

        List<String> ids = user.getCartItems().stream()
                .map(CartItem::getItemId)
                .collect(Collectors.toList());

        GetItemsListInput listInput = GetItemsListInput.builder().ids(ids).build();
        if (listInput.getIds().isEmpty()) {
            throw new NoItemsInCartException("Your cart is empty");
        }

        GetItemsListOutput items = zooStoreRestClient.getItemsList(listInput);

        BigDecimal totalPrice = items.getItemsList().stream()
                .map(i -> {
                    CartItem cartItem = user.getCartItems()
                            .stream()
                            .filter(filterItem -> filterItem.getItemId().equals(i.getId()))
                            .findFirst()
                            .orElseThrow(() -> new CartItemNotFoundException("cart item not found."));

                    BigDecimal itemTotalPrice = BigDecimal.valueOf(cartItem.getPrice().doubleValue() * cartItem.getQuantity());

                    GetAllItemsFromCartOutput output = GetAllItemsFromCartOutput.builder()
                            .id(i.getId())
                            .title(i.getTitle())
                            .description(i.getDescription())
                            .archived(i.isArchived())
                            .vendorID(i.getVendorID())
                            .multimedia(i.getMultimedia())
                            .tags(i.getTags())
                            .quantity(String.valueOf(cartItem.getQuantity()))
                            .price(String.valueOf(itemTotalPrice))
                            .build();

                    outputList.getItemsList().add(output);

                    return itemTotalPrice;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        outputList.setTotalPrice(totalPrice);
        return outputList;

    }
}
