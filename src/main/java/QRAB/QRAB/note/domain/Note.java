package QRAB.QRAB.note.domain;

import QRAB.QRAB.BaseTimeEntity;
import QRAB.QRAB.category.domain.Category;
import QRAB.QRAB.quiz.domain.QuizSet;
import QRAB.QRAB.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //기본 생성자를 자동 생성
@Table(name = "NOTE")
public class Note extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 값을 자동으로 생성
    @Column(name = "note_id")
    private Long id;
    private String title; //노트 제목
    @Column(columnDefinition = "longtext")
    private String content; //노트 내용

    @Column(columnDefinition = "longtext")
    private String chatgptContent; // chatgpt 가 요약한 내용
    @Column(columnDefinition = "longtext")
    private String url; //노트 url
    private String file;//파일 경로 - pdf, 사진
    private int restrictedAccess = 0; //기본값 0 -> 공개

    private int quizGenerationCount = 0; // 퀴즈 생성 횟수 (기본값 0)

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuizSet> quizSets;

    @Builder
    public Note(String title, String content, String chatgptContent, String url, String file, int restrictedAccess, User user, Category category, int quizGenerationCount){
        this.title = title;
        this.content = content;
        this.chatgptContent = chatgptContent;
        this.url = url;
        this.file = file;
        this.restrictedAccess =restrictedAccess;
        this.user = user;
        this.category = category;
        this.quizGenerationCount = quizGenerationCount;
    }
}
