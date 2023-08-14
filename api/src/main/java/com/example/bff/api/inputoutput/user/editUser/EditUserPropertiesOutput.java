package com.example.bff.api.inputoutput.user.editUser;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserPropertiesOutput implements OperationResult {

    private String id;
    private String name;
    private String password;
    private String phone;
    private String username;
    private String email;

}
