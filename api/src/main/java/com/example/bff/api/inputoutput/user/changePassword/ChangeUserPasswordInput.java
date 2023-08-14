package com.example.bff.api.inputoutput.user.changePassword;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.Email;
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
    private String newPassword;

}
