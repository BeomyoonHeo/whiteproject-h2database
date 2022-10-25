package site.metacoding.white.dto;

import org.junit.jupiter.api.Test;

import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.BoardResponseDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardResponseDto.BoardSaveRespDto.UserRespDto;

public class BoardSaveRespDtoTest {

    @Test
    public void innerclass_테스트() {
        User user = new User(1L, "ssar", "1234");
        Board boardTest = new Board(1L, "타이틀", "컨텐트", user);
        BoardSaveRespDto boardResponseDto = new BoardSaveRespDto(boardTest);


        UserRespDto userRespDto = new UserRespDto(user);
        boardResponseDto.setUser(userRespDto);
    }
    
}
