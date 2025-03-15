package online.gemfpt.BE.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import online.gemfpt.BE.model.AppointmentReportResponse;
import online.gemfpt.BE.model.ProgramReportResponse;
import online.gemfpt.BE.model.SurveyReportResponse;
import online.gemfpt.BE.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/surveys")
    @Operation(summary = "Báo cáo khảo sát tổng hợp",
            description = "Trả về báo cáo tổng hợp về các khảo sát, bao gồm số lượt nộp và điểm trung bình.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Báo cáo khảo sát được trả về thành công.")
    })
    public ResponseEntity<List<SurveyReportResponse>> getSurveyReport() {
        return ResponseEntity.ok(reportService.getSurveyReport());
    }

    @GetMapping("/appointments")
    @Operation(summary = "Báo cáo đặt lịch hẹn",
            description = "Trả về báo cáo về các lịch hẹn, bao gồm thông tin chi tiết và tổng số lịch hẹn.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Báo cáo lịch hẹn được trả về thành công.")
    })
    public ResponseEntity<List<AppointmentReportResponse>> getAppointmentReport() {
        return ResponseEntity.ok(reportService.getAppointmentReport());
    }

    @GetMapping("/programs")
    @Operation(summary = "Báo cáo tham gia chương trình hỗ trợ",
            description = "Trả về báo cáo về các chương trình hỗ trợ, bao gồm thông tin chi tiết và số người tham gia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Báo cáo chương trình được trả về thành công.")
    })
    public ResponseEntity<List<ProgramReportResponse>> getProgramReport() {
        return ResponseEntity.ok(reportService.getProgramReport());
    }
}