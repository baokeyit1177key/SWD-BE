package online.gemfpt.BE.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SurveyResponse {
    private Long id;
    private String title;
    private String description;
    private Long createdBy;
    private LocalDateTime createdAt;
    private List<QuestionResponse> questions;

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