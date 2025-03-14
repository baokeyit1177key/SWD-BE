package online.gemfpt.BE.model;

import lombok.Data;
import online.gemfpt.BE.enums.AppointmentStatus;
import java.time.LocalDateTime;

@Data
public class AppointmentResponse {
    private Long id;
    private String email;
    private String phoneNumber;
    private LocalDateTime appointmentDateTime;
    private String content;
    private AppointmentStatus status;
}
