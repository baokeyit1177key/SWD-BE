package online.gemfpt.BE.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Data
@ToString
public class SurveySubmitRequest {

    @NotNull
    private Long surveyId;          // ID của khảo sát

    private String email;

    @NotNull
    private String surveyName;      // Tên của khảo sát

    // Các trường userId và email sẽ được ghi đè từ người đang đăng nhập (không gửi từ FE)
    @NotNull
    private List<Answer> answers;   // Danh sách câu trả lời

    @Data
    @ToString
    public static class Answer {
        @NotNull
        private String questionText; // Nội dung câu hỏi từ client

        @NotNull
        private String answerText;   // Nội dung đáp án mà user chọn (thay cho answerId)

        @NotNull
        private int score;           // Điểm của đáp án (được gửi từ FE, nếu cần)
    }
}
