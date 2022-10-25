package site.metacoding.white.web;


import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserRequestDto.JoinReqDto;
import site.metacoding.white.dto.UserRequestDto.LoginReqDto;
import site.metacoding.white.dto.UserResponseDto.JoinRespDto;
import site.metacoding.white.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/join")
    public ResponseDto<?> userJoin(@RequestBody JoinReqDto joinReqDto) {
        JoinRespDto joinRespDto = userService.save(joinReqDto);
        return new ResponseDto<>(1, "ok", joinRespDto);
    }

    @PostMapping("/login")
    public ResponseDto<?> userLogin(@RequestBody LoginReqDto loginReqDto) {
        
        SessionUser principal = userService.login(loginReqDto);
        session.setAttribute("principal", principal);
        return new ResponseDto<>(1, "ok", principal);
    }
}
