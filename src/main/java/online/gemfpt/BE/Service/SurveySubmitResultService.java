package online.gemfpt.BE.Service;

import jakarta.persistence.EntityNotFoundException;
import online.gemfpt.BE.Repository.SurveyRepository;
import online.gemfpt.BE.Repository.SurveyResultRepository;
import online.gemfpt.BE.entity.Survey;
import online.gemfpt.BE.entity.SurveySubmitAnswer;
import online.gemfpt.BE.entity.SurveySubmitResult;
import online.gemfpt.BE.model.SurveySubmitRequest;
import online.gemfpt.BE.model.SurveySubmitResponse;
import online.gemfpt.BE.model.SurveySubmitResponse.QuestionAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SurveySubmitResultService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyResultRepository surveyResultRepository;

    /**
     * Xử lý submit khảo sát:
     * - Tính tổng điểm từ các câu trả lời dựa trên điểm được gửi từ FE.
     * - Lấy thông tin user (userId, email) từ request.
     * - Lưu kết quả vào DB.
     * - Trả về đối tượng SurveySubmitResponse với định dạng JSON yêu cầu.
     *
     * Format JSON trả về:
     * {
     *   "surveyId": 1,
     *   "surveyName": "Customer Satisfaction Survey",
     *   "userId": 123,
     *   "email": "user@example.com",
     *   "totalScore": 3,
     *   "answers": [
     *     {
     *       "questionId": null,
     *       "questionText": "What is 1 + 1?",
     *       "answerText": "2",
     *       "score": 1
     *     },
     *     ...
     *   ]
     * }
     */
    @Transactional
    public SurveySubmitResponse submitSurvey(SurveySubmitRequest request) {
        // Kiểm tra khảo sát tồn tại
        Survey survey = surveyRepository.findById(request.getSurveyId())
                .orElseThrow(() -> new EntityNotFoundException("Survey not found with id: " + request.getSurveyId()));

        // Lấy thông tin user từ request (không lấy từ SecurityContextHolder)
        String requestUserEmail = request.getEmail();

        int totalScore = 0;
        List<QuestionAnswer> questionAnswers = new ArrayList<>();

        // Duyệt qua từng câu trả lời gửi từ FE
        for (SurveySubmitRequest.Answer ans : request.getAnswers()) {
            // Ở đây, FE gửi lên questionText, answerText và score trực tiếp.
            totalScore += ans.getScore();
            questionAnswers.add(new QuestionAnswer(
                    null, // Nếu FE không gửi questionId, để null
                    ans.getQuestionText(),
                    ans.getAnswerText(),
                    ans.getScore()
            ));
        }

        // Tạo đối tượng SurveySubmitResult để lưu vào DB
        SurveySubmitResult result = new SurveySubmitResult();
        result.setSurveyId(request.getSurveyId());
        result.setSurveyName(request.getSurveyName());
        result.setEmail(requestUserEmail);
        result.setTotalScore(totalScore);
        result.setSubmittedAt(LocalDateTime.now());

        // Chuyển đổi danh sách câu trả lời từ DTO sang entity SurveySubmitAnswer
        List<SurveySubmitAnswer> answerEntities = new ArrayList<>();
        for (QuestionAnswer qa : questionAnswers) {
            SurveySubmitAnswer answerEntity = new SurveySubmitAnswer();
            answerEntity.setQuestionId(qa.getQuestionId());
            answerEntity.setQuestionText(qa.getQuestionText());
            answerEntity.setAnswerText(qa.getAnswerText());
            answerEntity.setScore(qa.getScore());
            answerEntity.setSurveySubmitResult(result);
            answerEntities.add(answerEntity);
        }
        result.setAnswers(answerEntities);

        SurveySubmitResult savedResult = surveyResultRepository.save(result);

        // Trả về SurveySubmitResponse với định dạng như yêu cầu
        return new SurveySubmitResponse(
                savedResult.getSurveyId(),
                savedResult.getSurveyName(),
                savedResult.getUserId(),
                savedResult.getEmail(),
                savedResult.getTotalScore(),
                questionAnswers
        );
    }

     // Lấy toàn bộ kết quả submit khảo sát
    @Transactional(readOnly = true)
    public List<SurveySubmitResult> getAllSurveyResults() {
        return surveyResultRepository.findAll();
    }

    // Lấy kết quả submit khảo sát theo email của người làm (user)
    @Transactional(readOnly = true)
    public List<SurveySubmitResult> getSurveyResultsByEmail(String email) {
        return surveyResultRepository.findByEmail(email);
    }

    // Lấy kết quả submit khảo sát theo surveyId
    @Transactional(readOnly = true)
    public List<SurveySubmitResult> getSurveyResultsBySurveyId(Long surveyId) {
        return surveyResultRepository.findBySurveyId(surveyId);
    }
}
