package site.metacoding.white.web;


import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserRequestDto.UserJoinReqDto;
import site.metacoding.white.dto.UserRequestDto.UserUpdateReqDto;
import site.metacoding.white.dto.UserResponseDto.UserJoinRespDto;
import site.metacoding.white.dto.UserResponseDto.UserUpdateRespDto;
import site.metacoding.white.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    //회원 정보 수정
    @PutMapping("/user/update")
    public ResponseDto<?> userUpdate(@RequestBody UserUpdateReqDto userUpdateReqDto) {
        SessionUser principal = (SessionUser) session.getAttribute("principal");
        userUpdateReqDto.setSessionUser(principal);
        UserUpdateRespDto userUpdateRespDto = userService.update(userUpdateReqDto);
        return new ResponseDto<>(1, "성공", userUpdateRespDto);
    }

    //회원 정보 미리보기
    @GetMapping("/user/{id}")
    public ResponseDto<?> userDetail(@PathVariable Long id) {
        return new ResponseDto<>(1, "ok", userService.userDetail(id));
    }

    //회원 가입
    @PostMapping("/join")
    public ResponseDto<?> userJoin(@RequestBody UserJoinReqDto joinReqDto) {
        UserJoinRespDto joinRespDto = userService.save(joinReqDto);
        return new ResponseDto<>(1, "ok", joinRespDto);
    }

    // //로그인
    // @PostMapping("/login")
    // public ResponseDto<?> userLogin(@RequestBody UserLoginReqDto loginReqDto) {
    //     SessionUser principal = userService.login(loginReqDto);
    //     session.setAttribute("principal", principal);
    //     return new ResponseDto<>(1, "ok", principal);
    // }
}
