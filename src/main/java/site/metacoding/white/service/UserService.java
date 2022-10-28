package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserRequestDto.UserJoinReqDto;
import site.metacoding.white.dto.UserRequestDto.UserLoginReqDto;
import site.metacoding.white.dto.UserRequestDto.UserUpdateReqDto;
import site.metacoding.white.dto.UserResponseDto.UserJoinRespDto;
import site.metacoding.white.dto.UserResponseDto.UserUpdateRespDto;
import site.metacoding.white.dto.UserResponseDto.UserUpdateRespDto.UserDetailRespDto;
import site.metacoding.white.util.SHA256;

// 트랜잭션 관리
// DTO 변환해서 컨트롤러에게 돌려줘야함

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SHA256 sha256;
    
    // 서비스메서드는 여러가지 책임을 가질 수 있다.
    // 응답의 DTO는 서비스에서 만든다.
    @Transactional // 트랜잭션을 붙이지 않으면 영속화 되어 있는 객체가 flush가 안됨.
    public UserJoinRespDto save(UserJoinReqDto joinReqDto) {

        //비밀번호 해시
        String encPassword = sha256.encrypt(joinReqDto.getPassword());
        joinReqDto.setPassword(encPassword);

        //회원정보 저장
        User userPS = userRepository.save(joinReqDto.toEntity());

        //DTO 리턴
        return new UserJoinRespDto(userPS);
    }// 트랜잭션 종료

    @Transactional(readOnly = true)
    public SessionUser login(UserLoginReqDto loginReqDto) {
        String encPassword = sha256.encrypt(loginReqDto.getPassword());
        User userPS = userRepository.findByUsername(loginReqDto.getUsername());
        if (userPS.getPassword().equals(encPassword)) {
            return new SessionUser(userPS);
        } else {
            throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
        }

    }

    @Transactional(readOnly = true)
    public UserDetailRespDto userDetail(Long id) {
        Optional<User> userOP = userRepository.findById(id);
        if (userOP.isEmpty()) {
            throw new RuntimeException("유저정보가 존재하지 않습니다.");
        }
        return new UserDetailRespDto(userOP.get());
    }
    
    @Transactional
    public UserUpdateRespDto update(UserUpdateReqDto userUpdateReqDto) {
        System.out.println(userUpdateReqDto.getSessionUser().getUsername());
        Optional<User> userOP = userRepository.findById(userUpdateReqDto.getSessionUser().getId());
        if (userOP.isEmpty()) {
            throw new RuntimeException("로그인상태를 확인해주세요");
        }
        userUpdateReqDto.setPassword(sha256.encrypt(userUpdateReqDto.getPassword()));
        User userPS = userUpdateReqDto.toEntity();

        return new UserUpdateRespDto(userPS);

    }
}
