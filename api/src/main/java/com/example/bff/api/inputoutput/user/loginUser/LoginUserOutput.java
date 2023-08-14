package com.example.bff.api.inputoutput.user.loginUser;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserOutput implements OperationResult {
    private String jwt;
}
