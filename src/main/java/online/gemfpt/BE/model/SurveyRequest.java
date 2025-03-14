package online.gemfpt.BE.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SurveyRequest {
    private String title;
    private String description;
    private Long createdBy;
    private List<QuestionRequest> questions;
}
