package com.example.bff.core.services.cart;

import com.example.bff.api.inputoutput.cart.changeItemQuantity.CartChangeItemQuantityOperation;
import com.example.bff.api.inputoutput.cart.changeItemQuantity.ChangeItemQuantityInput;
import com.example.bff.api.inputoutput.cart.changeItemQuantity.ChangeItemQuantityOutput;
import com.example.bff.core.exceptions.CartItemNotFoundException;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.core.exceptions.NotEnoughQuantityException;
import com.example.bff.core.exceptions.UserNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.UserRepository;
import com.example.zoostorestorage.api.inputoutput.item.getfromstorage.GetItemFromStorageOutput;
import com.example.zoostorestorage.restexport.ZooStorageRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartChangeItemQuantityOperationProcessor implements CartChangeItemQuantityOperation {

    private final ZooStorageRestClient zooStorageRestClient;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ChangeItemQuantityOutput process(ChangeItemQuantityInput changeItemQuantityInput) {

        try {
            zooStorageRestClient.getItemFromStorage(changeItemQuantityInput.getItemId());
        } catch (Exception e) {
            throw new ItemNotFoundException("Item not found in storage");
        }


        GetItemFromStorageOutput item = zooStorageRestClient.getItemFromStorage(changeItemQuantityInput.getItemId());

        if (changeItemQuantityInput.getQuantity() > Integer.parseInt(item.getQuantity())) {
            throw new NotEnoughQuantityException("Not enough quantity in storage");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UserNotFoundException("user not found"));

        CartItem cartItem = user.getCartItems().stream()
                .filter(filterItem -> filterItem.getItemId().equals(changeItemQuantityInput.getItemId()))
                .findFirst().orElseThrow(() -> new CartItemNotFoundException("cart item not found"));

        user.getCartItems().remove(cartItem);
        cartItem.setQuantity(changeItemQuantityInput.getQuantity());
        user.getCartItems().add(cartItem);
        cartItemRepository.save(cartItem);
        userRepository.save(user);


        ChangeItemQuantityOutput output = ChangeItemQuantityOutput.builder()
                .userId(user.getId().toString())
                .itemId(cartItem.getItemId())
                .quantity(cartItem.getQuantity())
                .build();

        return output;
    }
}
