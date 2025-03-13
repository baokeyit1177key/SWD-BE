package online.gemfpt.BE.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerOptionRequest {
    private String answerText;
    private int score;
}