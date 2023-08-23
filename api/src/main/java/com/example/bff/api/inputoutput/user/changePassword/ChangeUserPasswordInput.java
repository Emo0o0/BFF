package com.example.bff.api.inputoutput.user.changePassword;

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
public class ChangeUserPasswordInput implements OperationInput {

    @Email
    private String email;
    private String oldPassword;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 18)
    private String newPassword;

}
