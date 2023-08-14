package com.example.bff.api.inputoutput.user.loginUser;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInput implements OperationInput {

    @Email
    private String email;
    @NotBlank
    private String password;

}
