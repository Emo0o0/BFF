package com.example.bff.api.inputoutput.user.registerUser;

import com.example.bff.api.base.OperationResult;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserOutput implements OperationResult {


    private String id;
    private String name;
    private String password;
    private String phone;
    private String username;
    private String email;
}
