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

        List<String> ids = new ArrayList<>();
        for (CartItem cartItem : user.getCartItems()) {
            ids.add(cartItem.getItemId());
        }

        GetItemsListInput listInput = GetItemsListInput.builder().ids(ids).build();
        if (listInput.getIds().isEmpty()) {
            throw new NoItemsInCartException("Your cart is empty");
        }

        GetItemsListOutput items = zooStoreRestClient.getItemsList(listInput);

        BigDecimal totalPrice = BigDecimal.valueOf(0.0);
        for (ItemsToDtoSetMap i : items.getItemsList()) {

            CartItem cartItem = user.getCartItems()
                    .stream()
                    .filter(filterItem -> filterItem.getItemId().equals(i.getId()))
                    .findFirst()
                    .orElseThrow(() -> new CartItemNotFoundException("cart item not found."));

            totalPrice = totalPrice.add(BigDecimal.valueOf(cartItem.getPrice().doubleValue() * cartItem.getQuantity()));

            GetAllItemsFromCartOutput output = GetAllItemsFromCartOutput.builder()
                    .id(i.getId())
                    .title(i.getTitle())
                    .description(i.getDescription())
                    .archived(i.isArchived())
                    .vendorID(i.getVendorID())
                    .multimedia(i.getMultimedia())
                    .tags(i.getTags())
                    .quantity(String.valueOf(cartItem.getQuantity()))
                    .price(String.valueOf(cartItem.getPrice().doubleValue() * cartItem.getQuantity()))
                    .build();

            outputList.getItemsList().add(output);

        }
        outputList.setTotalPrice(totalPrice);
        return outputList;

    }
}
