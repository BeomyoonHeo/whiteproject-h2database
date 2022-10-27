package site.metacoding.white.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.Comment;
import site.metacoding.white.domain.User;

public class BoardResponseDto {


    @Getter
    @Setter
    public static class BoardSaveRespDto {
        private Long id;
        private String title;
        private String content;
        private String user;

        public BoardSaveRespDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.user = board.getUser().getUsername();
        }
    }

    @Setter
    @Getter
    public static class BoardDetailRespDto {
        private Long id;
        private String title;
        private String content;
        private BoardUserRespDto author;
        private List<CommentRespDto> comments = new ArrayList<>();

        public BoardDetailRespDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.author = new BoardUserRespDto(board.getUser());
            //List<CommentDto> <-- List<Comment>
            this.comments = board.getComments()
                .stream()
                .map((comment) -> new CommentRespDto(comment))
                .collect(Collectors.toList()); // foreach는 값을 return 해주지 않지만 map은 값을 return 해준다. -> 해당 객체로
            // for (Comment comment : board.getComments()) {
            //     this.comments.add(new CommentRespDto(comment));
            // }
        }

        @Getter
        @Setter
        public static class BoardUserRespDto {
            private Long id;
            private String username;

            public BoardUserRespDto(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }

        }

        @Getter
        @Setter
        public static class CommentUserRespDto {
            private Long id;
            private String username;

            public CommentUserRespDto(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }

        }
        @Getter
        @Setter
        public static class CommentRespDto {
            private Long id;
            private String content;
            private CommentUserRespDto commentUserRespDto;
    
            public CommentRespDto(Comment comment) {
                this.id = comment.getId();
                this.content = comment.getContent();
                this.commentUserRespDto = new CommentUserRespDto(comment.getUser());
            }
        }
    }

    @Getter
    public static class BoardAllRespDto {
        private String title;
        private BoardUserRespDto user;

        public BoardAllRespDto(Board board) {
            this.title = board.getTitle();
            this.user = new BoardUserRespDto(board.getUser());
        }
        @Getter
        @Setter
        public static class BoardUserRespDto {
            private Long id;
            private String username;

            public BoardUserRespDto(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }

        }
    }
    
    @Getter
    public static class BoardUpdateRespDto {
        private String title;
        private String content;
        private BoardUserRespDto user;

        public BoardUpdateRespDto(Board board) {
            this.title = board.getTitle();
            this.content = board.getContent();
            this.user = new BoardUserRespDto(board.getUser());
        }
    }
    @Getter
    @Setter
    public static class BoardUserRespDto {
        private Long id;
        private String username;

        public BoardUserRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }

    }
}
