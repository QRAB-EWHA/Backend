package QRAB.QRAB.bookmark.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkToggleRequestDTO {
    private Long quizId;
    private Integer status;  // 0: 북마크 삭제, 1: 북마크 생성
}
