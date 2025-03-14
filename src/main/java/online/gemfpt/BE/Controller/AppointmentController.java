package online.gemfpt.BE.Controller;

import online.gemfpt.BE.Service.AppointmentService;
import online.gemfpt.BE.Service.AuthenticationService;
import online.gemfpt.BE.entity.Account;
import online.gemfpt.BE.model.AppointmentRequest;
import online.gemfpt.BE.model.AppointmentResponse;
import online.gemfpt.BE.model.UpdateAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * POST /api/appointments - Đặt lịch hẹn với chuyên viên.
     */
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.createAppointment(request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/appointments - Lấy danh sách lịch hẹn.
     */
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> responses = appointmentService.getAllAppointments();
        return ResponseEntity.ok(responses);
    }

    /**
     * GET /api/appointments/{id} - Xem chi tiết lịch hẹn.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        AppointmentResponse response = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/appointments/{id} - Cập nhật lịch hẹn.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.updateAppointment(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/appointments/{id} - Hủy lịch hẹn.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        String result = appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(result);
    }




}
