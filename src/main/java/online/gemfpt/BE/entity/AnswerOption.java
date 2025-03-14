package online.gemfpt.BE.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "answer_options")
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionText; // Nội dung đáp án
    private int score; // Điểm của đáp án

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
