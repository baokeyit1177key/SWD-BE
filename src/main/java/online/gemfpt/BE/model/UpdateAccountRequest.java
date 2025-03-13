package online.gemfpt.BE.model;

import lombok.Getter;
import lombok.Setter;
import online.gemfpt.BE.enums.RoleEnum;

@Getter
@Setter
public class UpdateAccountRequest {
    private String accountName;
    private String email;
    private RoleEnum role;
}
