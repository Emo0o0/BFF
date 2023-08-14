package com.example.bff.api.inputoutput.user.changePassword;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserPasswordOutput implements OperationResult {

    private Boolean success;

}
