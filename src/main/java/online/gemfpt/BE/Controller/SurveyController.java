//package online.gemfpt.BE.Controller;
//
//import online.gemfpt.BE.Service.SurveyService;
//import online.gemfpt.BE.model.SurveyRequest;
//import online.gemfpt.BE.model.SurveyResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/surveys")
//public class SurveyController {
//
//    @Autowired
//    private SurveyService surveyService;
//
//    /**
//     * Tạo một khảo sát mới.
//     * Yêu cầu: SurveyRequest chứa thông tin tiêu đề, mô tả, người tạo và danh sách câu hỏi.
//     * Trả về: "OK" nếu khảo sát được tạo và lưu thành công.
//     */
//    @PostMapping
//    public ResponseEntity<String> createSurvey(@RequestBody SurveyRequest request) {
//        surveyService.createSurvey(request);
//        return ResponseEntity.ok("OK");
//    }
//
//    /**
//     * Lấy thông tin một khảo sát theo ID.
//     * @param id ID của khảo sát cần lấy.
//     * @return SurveyResponse chứa dữ liệu khảo sát, hoặc 404 nếu không tìm thấy.
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<SurveyResponse> getSurveyById(@PathVariable Long id) {
//        SurveyResponse response = surveyService.getSurveyById(id);
//        if (response == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(response);
//    }
//
//    /**
//     * Lấy danh sách tất cả các khảo sát.
//     * @return Danh sách SurveyResponse chứa thông tin các khảo sát.
//     */
//    @GetMapping
//    public ResponseEntity<List<SurveyResponse>> getAllSurveys() {
//        List<SurveyResponse> responses = surveyService.getAllSurveys();
//        return ResponseEntity.ok(responses);
//    }
//
//    @GetMapping("/test")
//public ResponseEntity<String> testEndpoint() {
//    return ResponseEntity.ok("test");
//}
//}
