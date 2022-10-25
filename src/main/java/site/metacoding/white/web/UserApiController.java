package site.metacoding.white.web;


import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.UserRequestDto.JoinReqDto;
import site.metacoding.white.dto.UserRequestDto.LoginReqDto;
import site.metacoding.white.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/join")
    public String userJoin(@RequestBody JoinReqDto joinReqDto) {
        userService.save(joinReqDto);
        
        return "ok";
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody LoginReqDto loginReqDto) {
        
        User principal = userService.login(loginReqDto);

        session.setAttribute("principal", principal);

        return "ok";
    }
}
