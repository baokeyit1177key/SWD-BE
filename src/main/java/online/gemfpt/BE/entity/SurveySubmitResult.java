package online.gemfpt.BE.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "survey_submit_results")
@Getter
@Setter
@ToString
public class SurveySubmitResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    private Long surveyId;         // ID của khảo sát
    private String surveyName;     // Tên khảo sát
    private Long userId;           // ID người làm khảo sát
    private String email;          // Email của người làm khảo sát
    private int totalScore;        // Tổng điểm của bài làm
    private LocalDateTime submittedAt; // Thời gian nộp bài

    @OneToMany(mappedBy = "surveySubmitResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveySubmitAnswer> answers = new ArrayList<>();
}
