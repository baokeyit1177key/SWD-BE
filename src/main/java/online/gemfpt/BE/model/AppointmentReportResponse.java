package online.gemfpt.BE.model;

import lombok.Data;
import online.gemfpt.BE.enums.AppointmentStatus;

import java.time.LocalDateTime;

@Data
public class AppointmentReportResponse {
    private Long appointmentId;
    private String email;
    private String phoneNumber;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;
    private int totalAppointments; // Tổng số lịch hẹn (cho báo cáo tổng hợp)
}