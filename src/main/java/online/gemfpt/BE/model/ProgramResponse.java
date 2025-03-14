package online.gemfpt.BE.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProgramResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int participantCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}