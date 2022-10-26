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

        public BoardSaveRespDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.user = new UserRespDto(board.getUser());
        }
    }

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

    @Setter
    @Getter
    public static class BoardDetailRespDto {
        private Long id;
        private String title;
        private String content;
        private String author;

        public BoardDetailRespDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.author = board.getUser().getUsername();
        }
    }

    @Getter
    public static class BoardAllRespDto {
        private String title;
        private String content;
        private UserRespDto user;


        public BoardAllRespDto(Board board) {
            this.title = board.getTitle();
            this.content = board.getContent();
            this.user = new UserRespDto(board.getUser());
        }
    }
    
}
