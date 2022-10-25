package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.User;

public class BoardResponseDto {

    @Getter
    @Setter
    public static class BoardSaveRespDto {
        private Long id;
        private String title;
        private String content;
        private UserRespDto user;

        @Getter
        @Setter
        public static class UserRespDto {
            private Long id;
            private String username;

            public UserRespDto(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }

        }

        public BoardSaveRespDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.user = new UserRespDto(board.getUser());
        }
        
    }
    
}
