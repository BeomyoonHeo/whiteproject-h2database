package site.metacoding.white.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.Comment;
import site.metacoding.white.domain.User;

public class CommentResponseDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommentSaveRespDto {
        private Long id;
        private String content;

        private UserDto user;
        private BoardDto board;

        public CommentSaveRespDto(Comment comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.board = new BoardDto(comment.getBoard());
            this.user = new UserDto(comment.getUser());
        }

        @Getter
        @Setter
        public static class UserDto {
            private Long id;
            private String username;

            public UserDto(User user) {
                this.id = user.getId(); // Lazy Loading
                this.username = user.getUsername();
            }

        }

        @Getter
        @Setter
        public static class BoardDto {
            private Long id;

            public BoardDto(Board board) {
                this.id = board.getId(); // Lazy Loading
            }
        }


    }
    
}
