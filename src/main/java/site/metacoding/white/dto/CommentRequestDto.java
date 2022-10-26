package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.Comment;

public class CommentRequestDto {

    @Getter
    @Setter
    public static class CommentSaveReqDto {
        private String content;
        private Long boardId;
        private SessionUser sessionUser; // 서비스로직

        public Comment toEntity(Board board) {
            return Comment.builder()
                .content(content)
                .board(board)
                .user(sessionUser.toEntity())
                .build();
        }
        
    }
}
