package com.example.bff.core.services.cart;

import com.example.bff.api.inputoutput.cart.dropCart.CartDropOperation;
import com.example.bff.api.inputoutput.cart.dropCart.DropCartInput;
import com.example.bff.api.inputoutput.cart.dropCart.DropCartOutput;
import com.example.bff.core.exceptions.UserNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartDropOperationProcessor implements CartDropOperation {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public DropCartOutput process(DropCartInput dropCartInput) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(
                authentication.getName()).orElseThrow(() -> new UserNotFoundException("User does not exist"));

        cartItemRepository.deleteAll(user.getCartItems());


        user.getCartItems().clear();
        userRepository.save(user);

        DropCartOutput output = DropCartOutput.builder()
                .success(true)
                .build();
        return output;

    }
}
