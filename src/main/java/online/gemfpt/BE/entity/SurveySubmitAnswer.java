package online.gemfpt.BE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "survey_submit_answers")
@Getter
@Setter
@ToString
public class SurveySubmitAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    private Long questionId;      // ID của câu hỏi
    private String questionText;  // Nội dung câu hỏi
    private String answerText;    // Đáp án đã chọn của người dùng
    private int score;            // Điểm của đáp án đó

    @ManyToOne
    @JoinColumn(name = "survey_submit_result_id", nullable = false)
    private SurveySubmitResult surveySubmitResult;
}
