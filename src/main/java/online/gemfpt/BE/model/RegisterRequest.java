package online.gemfpt.BE.model;

import com.google.api.client.util.DateTime;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import online.gemfpt.BE.enums.RoleEnum;

import java.util.Date;

@Data  // sai dc geter va setter cho class nay luon

@Getter
@Setter
public class RegisterRequest {
    String email;
    String name;
    String password;
    RoleEnum  role;
}
