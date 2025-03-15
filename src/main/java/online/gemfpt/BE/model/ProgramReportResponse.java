package online.gemfpt.BE.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProgramReportResponse {
    private Long programId;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int participantCount; // Số người tham gia
}