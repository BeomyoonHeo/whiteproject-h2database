package site.metacoding.white.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.service.UserService;
import site.metacoding.white.web.dto.ResponseDto;
import site.metacoding.white.web.dto.request.UserDto;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseDto<?> insert(@RequestBody UserDto userDto) {
        userService.insert(userDto);
        return new ResponseDto<>(1, "insert success", userDto);
    }
}
