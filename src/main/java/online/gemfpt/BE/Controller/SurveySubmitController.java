package online.gemfpt.BE.Controller;

import online.gemfpt.BE.Service.SurveySubmitResultService;
import online.gemfpt.BE.entity.SurveySubmitResult;
import online.gemfpt.BE.model.SurveySubmitRequest;
import online.gemfpt.BE.model.SurveySubmitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/surveysubmit")
public class SurveySubmitController {
    @Autowired
    private SurveySubmitResultService surveySubmitResultService;

      @PostMapping("/submit")
    public ResponseEntity<SurveySubmitResponse> submitSurvey(@RequestBody SurveySubmitRequest request) {
        SurveySubmitResponse response = surveySubmitResultService.submitSurvey(request);
        return ResponseEntity.ok(response);
    }
      /**
     * Lấy tất cả kết quả submit khảo sát.
     */
    @GetMapping
    public ResponseEntity<List<SurveySubmitResult>> getAllSurveyResults() {
        List<SurveySubmitResult> results = surveySubmitResultService.getAllSurveyResults();
        return ResponseEntity.ok(results);
    }

    /**
     * Lấy kết quả submit khảo sát theo email của người làm khảo sát.
     *
     * @param email Email của người làm khảo sát.
     * @return Danh sách kết quả submit.
     */
    @GetMapping("/by-email/{email}")
    public ResponseEntity<List<SurveySubmitResult>> getSurveyResultsByEmail(@PathVariable String email) {
        List<SurveySubmitResult> results = surveySubmitResultService.getSurveyResultsByEmail(email);
        return ResponseEntity.ok(results);
    }

    /**
     * Lấy kết quả submit khảo sát theo surveyId.
     *
     * @param surveyId ID của khảo sát.
     * @return Danh sách kết quả submit.
     */
    @GetMapping("/by-survey/{surveyId}")
    public ResponseEntity<List<SurveySubmitResult>> getSurveyResultsBySurveyId(@PathVariable Long surveyId) {
        List<SurveySubmitResult> results = surveySubmitResultService.getSurveyResultsBySurveyId(surveyId);
        return ResponseEntity.ok(results);
    }
}
