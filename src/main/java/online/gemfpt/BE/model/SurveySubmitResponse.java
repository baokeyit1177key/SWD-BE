package online.gemfpt.BE.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class SurveySubmitResponse {
    private Long surveyId;
    private String surveyName;
    private Long userId;
    private String email;
    private int totalScore;
    private List<QuestionAnswer> answers;

    @Data
    @AllArgsConstructor
    @ToString
    public static class QuestionAnswer {
        private Long questionId;
        private String questionText;
        private String answerText;
        private int score;
    }
}
