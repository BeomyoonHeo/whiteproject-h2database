package site.metacoding.white.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.UserRequestDto.JoinReqDto;
import site.metacoding.white.dto.UserRequestDto.LoginReqDto;
import site.metacoding.white.dto.UserResponseDto.JoinRespDto;

// 트랜잭션 관리
// DTO 변환해서 컨트롤러에게 돌려줘야함

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    
    // 응답의 DTO는 서비스에서 만든다.
    @Transactional // 트랜잭션을 붙이지 않으면 영속화 되어 있는 객체가 flush가 안됨.
    public JoinRespDto save(JoinReqDto joinReqDto) {
        User userPS = userRepository.save(joinReqDto.toEntity());
        return new JoinRespDto(userPS);
    }// 트랜잭션 종료

    @Transactional(readOnly = true)
    public User login(LoginReqDto loginReqDto) {
        User userPS = userRepository.findByUsername(loginReqDto.getUsername());
        if (userPS.getPassword().equals(loginReqDto.getPassword())) {
            return userPS;    
        } else {
            throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
        }
        
    }
}