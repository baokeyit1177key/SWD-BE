package online.gemfpt.BE.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerOptionResponse {
    private Long id;
    private String answerText;
    private int score;
}
