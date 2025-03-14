package online.gemfpt.BE.model;

import lombok.Data;
import online.gemfpt.BE.enums.RoleEnum;

@Data
public class UpdateAccountRoleByEmailRequest {
    private String email;      // Email của tài khoản cần cập nhật
    private RoleEnum role;     // Role mới cần cập nhật (ví dụ: ADMIN, PHUHUYNH, HOCSINH, CHUYENVIEN)
}
