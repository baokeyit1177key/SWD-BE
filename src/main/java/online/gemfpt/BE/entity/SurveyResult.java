package online.gemfpt.BE.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "survey_results")
@Getter
@Setter
public class SurveyResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long surveyId;
    private String surveyName;
    private Long userId;
    private String email;
    private int totalScore;
    private LocalDateTime submittedAt;
}
