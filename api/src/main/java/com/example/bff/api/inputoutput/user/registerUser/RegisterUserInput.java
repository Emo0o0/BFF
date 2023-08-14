package com.example.bff.api.inputoutput.user.registerUser;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserInput implements OperationInput {

    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String phone;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
}
