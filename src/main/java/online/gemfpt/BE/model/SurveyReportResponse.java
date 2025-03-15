package online.gemfpt.BE.model;

import lombok.Data;

@Data
public class SurveyReportResponse {
    private Long surveyId;
    private String surveyName;
    private int totalSubmissions; // Tổng số lượt nộp
    private double averageScore;  // Điểm trung bình
}