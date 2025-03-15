package online.gemfpt.BE.Service;

import online.gemfpt.BE.model.AppointmentReportResponse;
import online.gemfpt.BE.model.ProgramReportResponse;
import online.gemfpt.BE.model.SurveyReportResponse;
import online.gemfpt.BE.entity.Appointment;
import online.gemfpt.BE.entity.Program;
import online.gemfpt.BE.entity.SurveySubmitResult;
import online.gemfpt.BE.Repository.AppointmentRepository;
import online.gemfpt.BE.Repository.ProgramRepository;
import online.gemfpt.BE.Repository.SurveySubmitResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private SurveySubmitResultRepository surveySubmitResultRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ProgramRepository programRepository;

    public List<SurveyReportResponse> getSurveyReport() {
        List<SurveySubmitResult> results = surveySubmitResultRepository.findAll();

        // Nhóm theo surveyId và tính toán
        Map<Long, List<SurveySubmitResult>> groupedBySurvey = results.stream()
                .collect(Collectors.groupingBy(SurveySubmitResult::getSurveyId));

        return groupedBySurvey.entrySet().stream().map(entry -> {
            Long surveyId = entry.getKey();
            List<SurveySubmitResult> surveyResults = entry.getValue();

            int totalSubmissions = surveyResults.size();
            double averageScore = surveyResults.stream()
                    .mapToInt(SurveySubmitResult::getTotalScore)
                    .average()
                    .orElse(0.0);

            SurveyReportResponse response = new SurveyReportResponse();
            response.setSurveyId(surveyId);
            response.setSurveyName(surveyResults.get(0).getSurveyName()); // Lấy tên từ kết quả đầu tiên
            response.setTotalSubmissions(totalSubmissions);
            response.setAverageScore(averageScore);
            return response;
        }).collect(Collectors.toList());
    }

    public List<AppointmentReportResponse> getAppointmentReport() {
        List<Appointment> appointments = appointmentRepository.findAll();

        int totalAppointments = appointments.size();

        return appointments.stream().map(appointment -> {
            AppointmentReportResponse response = new AppointmentReportResponse();
            response.setAppointmentId(appointment.getId());
            response.setEmail(appointment.getEmail());
            response.setPhoneNumber(appointment.getPhoneNumber());
            response.setAppointmentDateTime(appointment.getAppointmentDateTime());
            response.setStatus(appointment.getStatus());
            response.setTotalAppointments(totalAppointments); // Tổng số lịch hẹn
            return response;
        }).collect(Collectors.toList());
    }

    public List<ProgramReportResponse> getProgramReport() {
        List<Program> programs = programRepository.findAll();

        return programs.stream().map(program -> {
            ProgramReportResponse response = new ProgramReportResponse();
            response.setProgramId(program.getId());
            response.setName(program.getName());
            response.setDescription(program.getDescription());
            response.setStartDate(program.getStartDate());
            response.setEndDate(program.getEndDate());
            response.setParticipantCount(program.getParticipants() != null ? program.getParticipants().size() : 0);
            return response;
        }).collect(Collectors.toList());
    }
}