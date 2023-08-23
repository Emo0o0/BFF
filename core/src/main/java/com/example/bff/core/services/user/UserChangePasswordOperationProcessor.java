package com.example.bff.core.services.user;

import com.example.bff.api.inputoutput.user.changePassword.ChangeUserPasswordInput;
import com.example.bff.api.inputoutput.user.changePassword.ChangeUserPasswordOutput;
import com.example.bff.api.inputoutput.user.changePassword.UserChangePasswordOperation;
import com.example.bff.core.exceptions.InvalidCredentialsException;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChangePasswordOperationProcessor implements UserChangePasswordOperation {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ChangeUserPasswordOutput process(ChangeUserPasswordInput input) {

        User user = userRepository.findByEmail(input.getEmail()).orElseThrow(RuntimeException::new);

        if (input.getOldPassword().isBlank()) {
            throw new RuntimeException();
        }
        if (!passwordEncoder.matches(input.getOldPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Wrong credentials");
        }

        user.setPassword(passwordEncoder.encode(input.getNewPassword()));

        userRepository.save(user);

        return ChangeUserPasswordOutput.builder()
                .success(true)
                .build();
    }
}
