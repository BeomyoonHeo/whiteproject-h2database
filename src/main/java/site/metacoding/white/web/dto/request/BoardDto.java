package site.metacoding.white.web.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.white.domain.Board;

@Data
@NoArgsConstructor
public class BoardDto {

    private String title;
    private String content;
    private String author;
    private Integer userNumber;

    public Board toEntity(BoardDto boardDto) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setAuthor(boardDto.getAuthor());
        return board;
    }

    public Board updateEntity(Board board) {
        board.setTitle(this.title);
        board.setContent(this.content);
        board.setAuthor(this.author);
        return board;
    }

}
