package com.example.bff.core.services.authentication;

import com.example.bff.api.inputoutput.user.loginUser.LoginUserInput;
import com.example.bff.api.inputoutput.user.loginUser.LoginUserOutput;
import com.example.bff.api.inputoutput.user.loginUser.UserLoginOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginOperationProcessor implements UserLoginOperation {

    private final JwtService jwtService;

    @Override
    public LoginUserOutput process(LoginUserInput loginUserInput) {
        return LoginUserOutput.builder().jwt(this.jwtService.generateJwt(loginUserInput)).build();
    }
}
