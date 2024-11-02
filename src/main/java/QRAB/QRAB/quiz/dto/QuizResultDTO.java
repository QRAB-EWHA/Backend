package QRAB.QRAB.quiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizResultDTO {
    private Long noteId;
    private String noteTitle;
    private int totalQuestions;
    private String createdDate;
    private String solvedDate;
    private String answerSummary;
    private String categoryName;
    private String parentCategoryName;

    public QuizResultDTO(Long noteId, String noteTitle, int totalQuestions, String createdDate, String solvedDate, String answerSummary, String categoryName, String parentCategoryName) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.totalQuestions = totalQuestions;
        this.createdDate = createdDate;
        this.solvedDate = solvedDate;
        this.answerSummary = answerSummary;
        this.categoryName = categoryName;
        this.parentCategoryName = parentCategoryName;
    }

}
