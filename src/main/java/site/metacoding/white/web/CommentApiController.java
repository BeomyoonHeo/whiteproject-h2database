package site.metacoding.white.web;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.dto.CommentRequestDto.CommentSaveReqDto;
import site.metacoding.white.dto.CommentResponseDto.CommentSaveRespDto;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentApiController {
    
    private final CommentService commentService;
    private final HttpSession session;
    
    @PostMapping("/s/comment")
    public ResponseDto<?> save(@RequestBody CommentSaveReqDto commentSaveReqDto) {

        SessionUser sessionUser = (SessionUser) session.getAttribute("principal");

        if (sessionUser == null) {
            throw new RuntimeException("로그인을 진행해주세요");
        }

        commentSaveReqDto.setSessionUser(sessionUser);

        CommentSaveRespDto commentSaveRespDto = commentService.save(commentSaveReqDto);

        return new ResponseDto<>(1, "ok", commentSaveRespDto);
    }

    @DeleteMapping("/s/comment/{id}")
    public ResponseDto<?> deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseDto<>(1, "ok", null);
    }


}
