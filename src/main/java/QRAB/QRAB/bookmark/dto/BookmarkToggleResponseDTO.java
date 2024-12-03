package QRAB.QRAB.bookmark.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkToggleResponseDTO {
    private Long bookmarkId;  // 생성된 경우에만 값 존재
    private Integer status;   // 현재 상태
    private String message;   // 처리 결과 메시지
}
