package online.gemfpt.BE.Service;

import online.gemfpt.BE.Repository.SurveyRepository;
import online.gemfpt.BE.entity.Question;
import online.gemfpt.BE.entity.Survey;
import online.gemfpt.BE.model.AnswerOptionResponse;
import online.gemfpt.BE.model.QuestionResponse;
import online.gemfpt.BE.model.SurveyRequest;
import online.gemfpt.BE.model.SurveyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Đánh dấu đây là một Service trong Spring, giúp quản lý bean tự động
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    /**
     * Tạo một khảo sát mới (Survey) từ dữ liệu nhận được từ client.
     * Sau khi lưu vào DB, chỉ trả về chuỗi "OK" cho người dùng.
     *
     * @param request SurveyRequest chứa thông tin tiêu đề, mô tả, người tạo và danh sách câu hỏi.
     * @return "OK" nếu khảo sát được tạo và lưu thành công.
     */
    @Transactional
    public String createSurvey(SurveyRequest request) {
        // Khởi tạo đối tượng Survey mới và gán thông tin cơ bản từ request
        Survey survey = new Survey();
        survey.setTitle(request.getTitle());
        survey.setDescription(request.getDescription());
        survey.setCreatedBy(request.getCreatedBy());

        // Xử lý danh sách câu hỏi từ request và chuyển thành danh sách Question entity
        List<Question> questions = request.getQuestions().stream().map(q -> {
            Question question = new Question();
            question.setQuestionText(q.getQuestionText());  // Gán nội dung câu hỏi
            question.setSurvey(survey);  // Liên kết câu hỏi với khảo sát

            // Chuyển danh sách đáp án từ request thành danh sách AnswerOption entity
            List<AnswerOption> answerOptions = q.getAnswerOptions().stream().map(a -> {
                AnswerOption answerOption = new AnswerOption();
                answerOption.setOptionText(a.getAnswerText());  // Gán nội dung đáp án
                answerOption.setScore(a.getScore());            // Gán điểm số
                answerOption.setQuestion(question);              // Liên kết đáp án với câu hỏi
                return answerOption;
            }).collect(Collectors.toList());

            question.setAnswerOptions(answerOptions);  // Gán danh sách đáp án vào câu hỏi
            return question;  // Trả về câu hỏi đã hoàn thành
        }).collect(Collectors.toList());

        // Gán danh sách câu hỏi vào khảo sát
        survey.setQuestions(questions);

        // Lưu khảo sát vào cơ sở dữ liệu
        surveyRepository.save(survey);

        // Trả về kết quả "OK" cho client
        return "OK";
    }

    /**
     * Hàm update Survey:
     * - Nhận id và SurveyRequest.
     * - Xóa khảo sát cũ theo id.
     * - Tạo một khảo sát mới dựa trên dữ liệu trong SurveyRequest.
     * - Trả về SurveyResponse của khảo sát mới được tạo.
     *
     * Lưu ý: Phương thức này "xóa và tạo lại" nên id của khảo sát mới có thể khác.
     *
     * @param id      ID của khảo sát cần cập nhật.
     * @param request SurveyRequest chứa dữ liệu cập nhật.
     * @return SurveyResponse của khảo sát mới được tạo.
     */
    @Transactional
    public SurveyResponse updateSurvey(Long id, SurveyRequest request) {
        // Tìm khảo sát cũ theo id, nếu không tìm thấy ném exception
        Survey existingSurvey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + id));
        // Xóa khảo sát cũ
        surveyRepository.delete(existingSurvey);

        // Tạo một khảo sát mới từ request (giống như createSurvey nhưng trả về SurveyResponse)
        Survey newSurvey = new Survey();
        newSurvey.setTitle(request.getTitle());
        newSurvey.setDescription(request.getDescription());
        newSurvey.setCreatedBy(request.getCreatedBy());

        // Xử lý danh sách câu hỏi và đáp án từ request
        List<Question> newQuestions = request.getQuestions().stream().map(q -> {
            Question question = new Question();
            question.setQuestionText(q.getQuestionText());
            question.setSurvey(newSurvey); // Liên kết câu hỏi với khảo sát mới

            List<AnswerOption> answerOptions = q.getAnswerOptions().stream().map(a -> {
                AnswerOption answerOption = new AnswerOption();
                answerOption.setOptionText(a.getAnswerText());
                answerOption.setScore(a.getScore());
                answerOption.setQuestion(question);
                return answerOption;
            }).collect(Collectors.toList());

            question.setAnswerOptions(answerOptions);
            return question;
        }).collect(Collectors.toList());
        newSurvey.setQuestions(newQuestions);

        // Lưu khảo sát mới vào DB
        Survey savedSurvey = surveyRepository.save(newSurvey);

        // Trả về SurveyResponse theo định dạng của hàm tạo survey
        return mapToSurveyResponse(savedSurvey);
    }

    /**
     * Xóa một khảo sát theo ID.
     *
     * @param id ID của khảo sát cần xóa.
     * @return "OK" nếu xóa thành công.
     */
    @Transactional
    public String deleteSurvey(Long id) {
        Optional<Survey> surveyOpt = surveyRepository.findById(id);
        if (surveyOpt.isPresent()) {
            surveyRepository.delete(surveyOpt.get());
            return "OK";
        } else {
            throw new RuntimeException("Survey not found with id: " + id);
        }
    }

    /**
     * Lấy một khảo sát theo ID.
     *
     * @param id ID của khảo sát cần lấy.
     * @return SurveyResponse chứa dữ liệu khảo sát hoặc null nếu không tìm thấy.
     */
    public SurveyResponse getSurveyById(Long id) {
        Optional<Survey> survey = surveyRepository.findById(id);
        return survey.map(this::mapToSurveyResponse).orElse(null);
    }

    /**
     * Lấy danh sách tất cả các khảo sát.
     *
     * @return List<SurveyResponse> danh sách khảo sát.
     */
    public List<SurveyResponse> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return surveys.stream().map(this::mapToSurveyResponse).collect(Collectors.toList());
    }

    /**
     * Chuyển đổi từ Survey entity sang SurveyResponse để trả về client.
     *
     * @param survey Survey entity cần chuyển đổi.
     * @return SurveyResponse.
     */
    private SurveyResponse mapToSurveyResponse(Survey survey) {
        SurveyResponse response = new SurveyResponse();
        response.setId(survey.getId());
        response.setTitle(survey.getTitle());
        response.setDescription(survey.getDescription());
        response.setCreatedBy(survey.getCreatedBy());
        response.setCreatedAt(survey.getCreatedAt());

        List<QuestionResponse> questionResponses = survey.getQuestions().stream().map(q -> {
            QuestionResponse qr = new QuestionResponse();
            qr.setId(q.getId());
            qr.setQuestionText(q.getQuestionText());

            List<AnswerOptionResponse> answerResponses = q.getAnswerOptions().stream().map(a -> {
                AnswerOptionResponse ar = new AnswerOptionResponse();
                ar.setId(a.getId());
                ar.setAnswerText(a.getOptionText());
                ar.setScore(a.getScore());
                return ar;
            }).collect(Collectors.toList());

            qr.setAnswerOptions(answerResponses);
            return qr;
        }).collect(Collectors.toList());

        response.setQuestions(questionResponses);
        return response;
    }
}
