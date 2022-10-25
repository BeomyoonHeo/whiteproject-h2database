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
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.BoardRequestDto.BoardSaveReqDto;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;
    private final HttpSession session;
    //import alt + shift + o

    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id) {

        Board boardPS = boardService.findById(id);
        System.out.println(boardPS.getTitle());
        System.out.println(boardPS.getContent());
        System.out.println(boardPS.getUser().getUsername());
        System.out.println(boardPS.getUser().getPassword());
        System.out.println(boardPS.getUser().getId());
        return "OK";
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
    public String saveV2(@RequestBody BoardSaveReqDto boardSaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        boardSaveReqDto.newInstance();
        boardSaveReqDto.getServiceDto().setUser(principal);
        // insert into board(title, content, user_id), value(?, ?, ?)
        boardService.save(boardSaveReqDto); // 서비스에는 단 하나의 객체만 전달한다.

        return "ok";
    }
}
