package online.gemfpt.BE.model;

import lombok.Data;
import online.gemfpt.BE.enums.RoleEnum;

@Data
public class UpdateAccountRequest {
    private Long id;        // ID của tài khoản cần cập nhật
    private String email;   // Email mới (nếu có)
    private String name;    // Tên mới (nếu có)
    private String password; // Mật khẩu mới (nếu có)

    private RoleEnum role;   // Role mới (ADMIN, PHUHUYNH, HOCSINH, CHUYENVIEN)
}
