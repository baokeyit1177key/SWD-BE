package online.gemfpt.BE.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String authorName; // Tên tác giả để frontend hiển thị
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}