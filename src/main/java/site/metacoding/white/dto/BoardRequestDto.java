package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.Board;


public class BoardRequestDto {

    @Getter
    @Setter
    public static class BoardSaveReqDto {
        private String title;
        private String content;
        private SessionUser sessionUser;

        public Board toEntity() {
            return Board.builder().title(title).content(content).user(sessionUser.toEntity()).build();
        }
    }

    @Getter
    @Setter
    public static class BoardUpdateReqDto {
        private Long id;
        private String title;
        private String content;
        private SessionUser sessionUser;

        public Board toEntity() {
            return Board.builder().id(id).title(title).content(content).user(sessionUser.toEntity()).build();
        }
    }
    // DTO는 여기다가 추가로
}
