package com.example.bff.core.services.cart;

import com.example.bff.api.inputoutput.cart.removeItemFromCart.CartRemoveItemOperation;
import com.example.bff.api.inputoutput.cart.removeItemFromCart.RemoveItemFromCartInput;
import com.example.bff.api.inputoutput.cart.removeItemFromCart.RemoveItemFromCartOutput;
import com.example.bff.core.exceptions.CartItemNotFoundException;
import com.example.bff.core.exceptions.UserNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartRemoveItemOperationProcessor implements CartRemoveItemOperation {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public RemoveItemFromCartOutput process(RemoveItemFromCartInput removeItemFromCartInput) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(
                authentication.getName()).orElseThrow(() -> new UserNotFoundException("User does not exist"));

        CartItem cartItem = user.getCartItems()
                .stream()
                .filter(cartItemFilter -> cartItemFilter.getItemId().equals(removeItemFromCartInput.getItemId()))
                .findFirst()
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));

        user.getCartItems().remove(cartItem);
        userRepository.save(user);
        cartItemRepository.delete(cartItem);


        RemoveItemFromCartOutput output = RemoveItemFromCartOutput.builder()
                .success(true)
                .build();
        return output;

    }
}
