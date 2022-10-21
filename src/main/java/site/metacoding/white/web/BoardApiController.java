package site.metacoding.white.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;
    //import alt + shift + o

    @GetMapping("/board/{id}")
    public Board findById(@PathVariable Long id) {
        
        return boardService.findById(id);
    }

    @PostMapping("/board")
    public String save(@RequestBody Board board) {
        boardService.save(board);

        return "ok";
    }
}
