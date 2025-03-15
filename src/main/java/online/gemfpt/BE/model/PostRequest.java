package online.gemfpt.BE.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String content;

    private Long authorId; // ID của tác giả (Account)
}