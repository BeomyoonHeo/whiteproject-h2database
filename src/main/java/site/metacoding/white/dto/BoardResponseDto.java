package site.metacoding.white.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.white.domain.Board;


public class BoardResponseDto {

    @Getter
    @Setter
    public static class BoardCreateDto {
        private Long id;
        private String title;
        private String content;

        public BoardCreateDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BoardDetailDto {
        private Long id;
        private String title;
        private String content;

        public BoardDetailDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }
    
    
}
