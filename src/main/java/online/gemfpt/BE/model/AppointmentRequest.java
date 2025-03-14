package online.gemfpt.BE.model;

import lombok.Data;
import online.gemfpt.BE.enums.AppointmentStatus;
import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private String email;
    private String phoneNumber;
    private LocalDateTime appointmentDateTime;
    private String content;
    // Trong tạo mới, FE có thể không gửi trường này và backend mặc định là PENDING,
    // đối với update, FE có thể gửi giá trị khác.
    private AppointmentStatus status;
}
