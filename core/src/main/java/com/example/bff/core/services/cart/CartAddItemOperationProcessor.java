package com.example.bff.core.services.cart;

import com.example.bff.api.inputoutput.cart.addItemToCart.AddItemToCartInput;
import com.example.bff.api.inputoutput.cart.addItemToCart.AddItemToCartOutput;
import com.example.bff.api.inputoutput.cart.addItemToCart.CartAddItemOperation;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.core.exceptions.NotEnoughQuantityException;
import com.example.bff.core.exceptions.UserNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.UserRepository;
import com.example.zoostorestorage.api.inputOutput.getItemFromStorage.GetItemFromStorageOutput;
import com.example.zoostorestorage.restexport.ZooStorageRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartAddItemOperationProcessor implements CartAddItemOperation {

    private final ZooStorageRestClient zooStorageRestClient;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public AddItemToCartOutput process(AddItemToCartInput addItemToCartInput) {

        try {
            zooStorageRestClient.getItemFromStorage(addItemToCartInput.getItemId());
        } catch (Exception e) {
            throw new ItemNotFoundException("Item was not found in storage");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(
                authentication.getName()).orElseThrow(() -> new UserNotFoundException("User does not exist"));

        GetItemFromStorageOutput storageItem = zooStorageRestClient.getItemFromStorage(addItemToCartInput.getItemId());

        if (Integer.parseInt(addItemToCartInput.getQuantity()) > Integer.parseInt(storageItem.getQuantity())) {
            throw new NotEnoughQuantityException("Not enough quantity in storage");
        }

        Optional<CartItem> optionalCartItem = user.getCartItems()
                .stream()
                .filter(streamItem -> streamItem.getItemId().equals(addItemToCartInput.getItemId()))
                .findFirst();

        if (optionalCartItem.isPresent()) {

            CartItem cartItem = optionalCartItem.get();
            if (Integer.parseInt(storageItem.getQuantity()) == cartItem.getQuantity()) {
                throw new NotEnoughQuantityException("Not enough quantity in storage");
            }

            user.getCartItems().remove(cartItem);
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
            user.getCartItems().add(cartItem);
            userRepository.save(user);

            AddItemToCartOutput output = AddItemToCartOutput.builder()
                    .itemId(cartItem.getItemId())
                    .userId(cartItem.getUser().getId().toString())
                    .quantity(cartItem.getQuantity())
                    .build();

            return output;
        }


        CartItem cartItem = CartItem.builder()
                .itemId(addItemToCartInput.getItemId())
                .quantity(Integer.parseInt(addItemToCartInput.getQuantity()))
                .price(BigDecimal.valueOf(Double.valueOf(storageItem.getPrice())))
                .user(user)
                .build();

        cartItemRepository.save(cartItem);
        user.getCartItems().add(cartItem);
        userRepository.save(user);

        AddItemToCartOutput output = AddItemToCartOutput.builder()
                .itemId(cartItem.getItemId())
                .userId(cartItem.getUser().getId().toString())
                .quantity(cartItem.getQuantity())
                .build();

        return output;

    }
}
