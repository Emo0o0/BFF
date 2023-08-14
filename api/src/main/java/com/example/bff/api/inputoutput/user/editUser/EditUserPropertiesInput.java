package com.example.bff.api.inputoutput.user.editUser;

import com.example.bff.api.base.OperationInput;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserPropertiesInput implements OperationInput {

    private String id;
    private String name;
    private String password;
    private String phone;
    private String username;
    private String email;
}
