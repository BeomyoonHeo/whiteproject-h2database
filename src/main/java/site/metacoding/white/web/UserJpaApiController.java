package site.metacoding.white.web;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.LoadedTypeInitializer.ForStaticField;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserJpaRepository;
import site.metacoding.white.dto.ResponseDto;

@RestController
@RequiredArgsConstructor
public class UserJpaApiController {
    
    //findById, findAll, save, deleteById
    
    private final UserJpaRepository userJpaRepository;

    @GetMapping("/jpa/user/{id}")
    public ResponseDto<?> findById(@PathVariable Long id) {
        Optional<User> userOP = userJpaRepository.findById(id);
        if (userOP.isPresent()) {
            return new ResponseDto<>(1, "성공", userOP.get());
        }
        throw new RuntimeException("해당 사용자를 찾을 수 없습니다.");

    }
    
    @PostMapping("/jpa/join")
    public ResponseDto<?> save(@RequestBody User user) {
        User userPS = userJpaRepository.save(user);

        return new ResponseDto<>(1, "성공", userPS);
    }

    @PostMapping("/jpa/login")
    public ResponseDto<?> login(@RequestBody User user) {
        User userPS = userJpaRepository.findByUsername(user.getUsername());

        if (userPS.getPassword().equals(user.getPassword())) {
            return new ResponseDto<>(1, "성공", userPS);
        } else {
            throw new RuntimeException("아이디 비밀번호가 틀렸습니다.");
        }

    }
    
    @GetMapping("/jpa/user")
    public ResponseDto<?> findAll(Integer page) {
        PageRequest pr = PageRequest.of(page, 2);
        Page<User> userList = userJpaRepository.findAll(pr);

        return new ResponseDto<>(1, "성공", userList);
    }
        
}
