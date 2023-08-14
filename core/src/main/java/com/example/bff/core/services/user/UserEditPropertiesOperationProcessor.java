package com.example.bff.core.services.user;

import com.example.bff.api.inputoutput.user.editUser.EditUserPropertiesInput;
import com.example.bff.api.inputoutput.user.editUser.EditUserPropertiesOutput;
import com.example.bff.api.inputoutput.user.editUser.UserEditPropertiesOperation;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEditPropertiesOperationProcessor implements UserEditPropertiesOperation {

    private final UserRepository userRepository;

    @Override
    public EditUserPropertiesOutput process(EditUserPropertiesInput input) {

        if (!userRepository.existsById(UUID.fromString(input.getId()))) {
            throw new RuntimeException("User not found");
        }

        Optional<User> optionalUser = userRepository.findById(UUID.fromString(input.getId()));
        User user = optionalUser.get();

        if (!input.getName().isBlank()) {
            user.setName(input.getName());
        }
        if (!input.getPassword().isBlank()) {
            user.setPassword(input.getPassword());
        }
        if (!input.getPhone().isBlank()) {
            user.setPhone(input.getPhone());
        }
        if (!input.getUsername().isBlank()) {
            user.setUsername(input.getUsername());
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
