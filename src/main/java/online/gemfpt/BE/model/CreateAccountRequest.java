package online.gemfpt.BE.model;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String email;
    private String password;
    private String name;
}
