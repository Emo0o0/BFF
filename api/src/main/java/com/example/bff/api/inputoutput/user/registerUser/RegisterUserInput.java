package com.example.bff.api.inputoutput.user.registerUser;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserInput implements OperationInput {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 5, max = 100)
    private String name;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 18)
    private String password;
    @NotBlank(message = "Phone cannot be empty")
    @Size(min = 6, max = 15)
    private String phone;
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 4, max = 15)
    private String username;
    @NotBlank(message = "Email cannot be empty")
    @Email
    private String email;
}
