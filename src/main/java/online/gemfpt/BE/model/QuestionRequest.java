package online.gemfpt.BE.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class QuestionRequest {
    private String questionText;  // Nội dung câu hỏi
    private List<AnswerOptionRequest> answerOptions;  // Danh sách các đáp án
}
