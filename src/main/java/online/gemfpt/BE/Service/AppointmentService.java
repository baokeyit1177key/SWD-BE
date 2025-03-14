package online.gemfpt.BE.Service;

import online.gemfpt.BE.Repository.AppointmentRepository;
import online.gemfpt.BE.entity.Appointment;
import online.gemfpt.BE.enums.AppointmentStatus;
import online.gemfpt.BE.model.AppointmentRequest;
import online.gemfpt.BE.model.AppointmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * POST /api/appointments - Đặt lịch hẹn với chuyên viên.
     * Khi tạo mới, trạng thái tự động được gán là AppointmentStatus.PENDING.
     */
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Appointment appointment = new Appointment();
        appointment.setEmail(request.getEmail());
        appointment.setPhoneNumber(request.getPhoneNumber());
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setContent(request.getContent());
        // Đặt trạng thái mặc định là PENDING khi tạo mới
        appointment.setStatus(AppointmentStatus.PENDING);

        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponse(saved);
    }

    /**
     * GET /api/appointments - Lấy danh sách lịch hẹn.
     */
    public List<AppointmentResponse> getAllAppointments() {
        List<Appointment> list = appointmentRepository.findAll();
        return list.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * GET /api/appointments/{id} - Xem chi tiết lịch hẹn.
     */
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        return mapToResponse(appointment);
    }

    /**
     * PUT /api/appointments/{id} - Cập nhật lịch hẹn.
     */
    @Transactional
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        // Cập nhật thông tin
        appointment.setEmail(request.getEmail());
        appointment.setPhoneNumber(request.getPhoneNumber());
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setContent(request.getContent());
        // Nếu FE gửi status, cập nhật; nếu không, giữ nguyên.
        if (request.getStatus() != null) {
            appointment.setStatus(request.getStatus());
        }
        Appointment updated = appointmentRepository.save(appointment);
        return mapToResponse(updated);
    }

    /**
     * DELETE /api/appointments/{id} - Hủy lịch hẹn.
     */
    @Transactional
    public String deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        appointmentRepository.delete(appointment);
        return "Appointment deleted successfully";
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setEmail(appointment.getEmail());
        response.setPhoneNumber(appointment.getPhoneNumber());
        response.setAppointmentDateTime(appointment.getAppointmentDateTime());
        response.setContent(appointment.getContent());
        response.setStatus(appointment.getStatus());
        return response;
    }
}
