package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.User;


public class BoardRequestDto {

    @Getter
    @Setter
    public static class BoardSaveReqDto {
        private String title;
        private String content;

        private ServiceDto serviceDto;

        // 클라이언트한테 받는게 아님
        public void newInstance() {
            this.serviceDto = new ServiceDto();
        }

        @Getter
        @Setter
        public class ServiceDto {
            private SessionUser sessionUser;
            
            public User toEntity() {
                return User.builder().username(sessionUser.getUsername()).id(sessionUser.getId()).build();
            }
        }

        public Board SavetoEntity(BoardSaveReqDto boardSaveReqDto) {
            Board board = Board.builder().title(boardSaveReqDto.getTitle()).content(boardSaveReqDto.getContent())
                    .user(boardSaveReqDto.getServiceDto().toEntity()).build();
            return board;
        }
    }
    // DTO는 여기다가 추가로
}
