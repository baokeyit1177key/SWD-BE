package online.gemfpt.BE.Controller;

import online.gemfpt.BE.Service.SurveyService;
import online.gemfpt.BE.model.SurveyRequest;
import online.gemfpt.BE.model.SurveyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

     @PostMapping("/creatervey")
    public ResponseEntity<String> createSurvey(@RequestBody SurveyRequest request) {
        surveyService.createSurvey(request);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/get-all-sevey")
    public ResponseEntity<List<SurveyResponse>> getAllSurveys() {
        List<SurveyResponse> responses = surveyService.getAllSurveys();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get-sevey{id}")
    public ResponseEntity<SurveyResponse> getSurveyById(@PathVariable Long id) {
        SurveyResponse response = surveyService.getSurveyById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

      @PutMapping("/Update-servey-{id}")
    public ResponseEntity<SurveyResponse> updateSurvey(@PathVariable Long id, @RequestBody SurveyRequest request) {
        SurveyResponse response = surveyService.updateSurvey(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Xóa một khảo sát theo ID.
     * @param id ID của khảo sát cần xóa.
     * @return "OK" nếu xóa thành công.
     */
    @DeleteMapping("/deleteservey{id}")
    public ResponseEntity<String> deleteSurvey(@PathVariable Long id) {
        String result = surveyService.deleteSurvey(id);
        return ResponseEntity.ok(result);
    }
}
