package site.metacoding.white.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.service.BoardService;
import site.metacoding.white.web.dto.ResponseDto;
import site.metacoding.white.web.dto.request.BoardDto;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseDto<?> save(@RequestBody BoardDto boardDto) {
        boardService.save(boardDto);
        return new ResponseDto<>(1, "post success", boardDto);
    }

    @DeleteMapping("/board/{id}")
    public ResponseDto<?> deleteById(@PathVariable Long id) {
        boardService.deleteById(id);
        return new ResponseDto<>(1, "delete success", id);
    }

    @PutMapping("/board/{id}")
    public ResponseDto<?> update(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        boardService.update(id, boardDto);
        return new ResponseDto<>(1, "update success", boardDto);
    }

    @GetMapping("/board")
    public ResponseDto<?> findAll() {
        return new ResponseDto<>(1, "success", boardService.findAll());
    }

    @GetMapping("/board/getuser")
    public ResponseDto<?> findByAuthorForUser(@RequestBody String userName) {
        System.out.println(userName);
        return new ResponseDto<>(1, "search Success", boardService.findByAuthorForUser(userName));
    }
}
