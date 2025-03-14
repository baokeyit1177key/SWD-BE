package online.gemfpt.BE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import online.gemfpt.BE.enums.AppointmentStatus;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@ToString
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID tự sinh

    @Column(nullable = false)
    private String email; // Email của người đặt lịch

    @Column(nullable = false)
    private String phoneNumber; // Số điện thoại (sdt)

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime; // Ngày giờ đặt lịch

    @Column(columnDefinition = "TEXT")
    private String content; // Nội dung (mô tả, yêu cầu, v.v.)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status; // Trạng thái (ví dụ: PENDING)
}
