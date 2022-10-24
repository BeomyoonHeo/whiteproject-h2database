package site.metacoding.white.web;


import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.UserRequestDto.userJoinDto;
import site.metacoding.white.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/join")
    public String userJoin(@RequestBody userJoinDto userjoinDto) {
        userService.save(userjoinDto);
        
        return "ok";
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody User user) {
        
        User principal = userService.login(user);

        session.setAttribute("principal", principal);

        return "ok";
    }
}
