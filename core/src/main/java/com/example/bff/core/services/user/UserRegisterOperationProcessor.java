package com.example.bff.core.services.user;

import com.example.bff.api.inputoutput.user.registerUser.RegisterUserInput;
import com.example.bff.api.inputoutput.user.registerUser.RegisterUserOutput;
import com.example.bff.api.inputoutput.user.registerUser.UserRegisterOperation;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterOperationProcessor implements UserRegisterOperation {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserOutput process(RegisterUserInput input) {

        User user = User.builder()
                .name(input.getName())
                .password(passwordEncoder.encode(input.getPassword()))
                .phone(input.getPhone())
                .username(input.getUsername())
                .email(input.getEmail())
                .build();

        userRepository.save(user);

        RegisterUserOutput output = RegisterUserOutput.builder()
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
