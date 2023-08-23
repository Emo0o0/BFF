package com.example.bff.core.services.user;

import com.example.bff.api.inputoutput.user.editUser.EditUserPropertiesInput;
import com.example.bff.api.inputoutput.user.editUser.EditUserPropertiesOutput;
import com.example.bff.api.inputoutput.user.editUser.UserEditPropertiesOperation;
import com.example.bff.core.exceptions.UserNotFoundException;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEditPropertiesOperationProcessor implements UserEditPropertiesOperation {

    private final UserRepository userRepository;

    @Override
    public EditUserPropertiesOutput process(EditUserPropertiesInput input) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(
                authentication.getName()).orElseThrow(() -> new UserNotFoundException("User does not exist"));

        if (!input.getName().isBlank()) {
            if (input.getName().length() >= 5 && input.getName().length() <= 100) {
                user.setName(input.getName());
            }
        }
        if (!input.getPassword().isBlank()) {
            if (input.getPassword().length() >= 6 && input.getPassword().length() <= 18) {
                user.setPassword(input.getPassword());
            }
        }
        if (!input.getPhone().isBlank()) {
            if (input.getPhone().length() >= 6 && input.getPhone().length() <= 15) {
                user.setPhone(input.getPhone());
            }
        }
        if (!input.getUsername().isBlank()) {
            if (input.getUsername().length() >= 4 && input.getUsername().length() <= 15) {
                user.setUsername(input.getUsername());
            }
        }
        if (!input.getEmail().isBlank()) {
            user.setEmail(input.getEmail());
        }

        userRepository.save(user);

        EditUserPropertiesOutput output = EditUserPropertiesOutput.builder()
                .id(user.getId().toString())
                .name(user.getName())
                .password(user.getPassword())
                .phone(user.getPhone())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        return output;
    }
}
