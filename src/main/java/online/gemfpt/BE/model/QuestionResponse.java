package online.gemfpt.BE.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionResponse {
    private Long id;
    private String questionText;
    private List<AnswerOptionResponse> answerOptions;
}
