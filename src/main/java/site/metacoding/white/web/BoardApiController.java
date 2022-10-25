package site.metacoding.white.web;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.dto.BoardRequestDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardResponseDto.BoardCreateDto;
import site.metacoding.white.dto.BoardResponseDto.BoardDetailDto;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;
    private final HttpSession session;
    //import alt + shift + o

    @GetMapping("/board/{id}")
    public ResponseDto<?> findById(@PathVariable Long id) {

        BoardDetailDto boardDetailDto = boardService.findById(id);
        return new ResponseDto<>(1, "ok", boardDetailDto);
    }

    @PutMapping("/board/{id}")
    public String update(@PathVariable Long id, @RequestBody Board board) {
        boardService.update(id, board);
        return "ok";
    }

    @DeleteMapping("/board/{id}")
    public String delete(@PathVariable Long id) {
        boardService.deleteById(id);
        return "ok";
    }
    @GetMapping("/board")
    public List<Board> findAll() {
        return boardService.findAll();
    }

    @PostMapping("/board")
    public ResponseDto<?> saveV2(@RequestBody BoardSaveReqDto boardSaveReqDto) {
        SessionUser principal = (SessionUser) session.getAttribute("principal");
        boardSaveReqDto.newInstance();
        boardSaveReqDto.getServiceDto().setSessionUser(principal);
        // insert into board(title, content, user_id), value(?, ?, ?)
        BoardCreateDto boardCreateDto = boardService.save(boardSaveReqDto); // 서비스에는 단 하나의 객체만 전달한다.

        return new ResponseDto<>(1, "ok", boardCreateDto);
    }
}
