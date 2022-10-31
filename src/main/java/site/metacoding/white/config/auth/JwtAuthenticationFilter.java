package site.metacoding.white.config.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserRequestDto.UserLoginReqDto;
import site.metacoding.white.util.SHA256;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private final UserRepository userRepository; // DI (FilterConfig 주입받음)

    // /login 요청시
    // post 요청시
    // username, password (json)
    // db확인
    // 토큰 생성
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // 각종 메서드를 사용하기위해 자식클래스로 다운캐스팅
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Post요청이 아닌것을 거부
        if (!req.getMethod().equals("POST")) {
            customResponse("로그인시에는 post요청을 해야 합니다.", resp);
        }
        

        // Body 값 받기
        ObjectMapper om = new ObjectMapper();
        UserLoginReqDto userLoginReqDto = om.readValue(req.getInputStream(), UserLoginReqDto.class);
        log.debug("디버그 : " + userLoginReqDto.getUsername());
        log.debug("디버그 : " + userLoginReqDto.getPassword());

        Optional<User> userOP = userRepository.findByUsername(userLoginReqDto.getUsername());

        // 유저네임 있는지 체크
        if (userOP.isEmpty()) {
            // findByUsername 나중에 처리하기
            customResponse("유저네임을 찾을 수 없습니다.", resp);
            return;
        }

        //패스워드 체크
        User userPS = userOP.get();
        SHA256 sh = new SHA256();
        String encPassword = sh.encrypt(userLoginReqDto.getPassword());
        if (!userPS.getPassword().equals(encPassword)) {

            // findByUsername 나중에 처리하기
            customResponse("패스워드가 틀렸습니다.", resp);
            return;
        }
        
        // JWT 토큰 생성 1초 = 1 / 1000
        Date expire = new Date(System.currentTimeMillis()+(1000 * 60 * 60)); // 1시간
        String jwtToken = JWT.create()
            .withSubject("메타코딩") //토큰이름
            .withExpiresAt(expire) // 만료시간
            .withClaim("id", userPS.getId()) // 페이로드
            .withClaim("username", userPS.getUsername()) // 페이로드
            .sign(Algorithm.HMAC512("뺑소니")); //시그니쳐 - 어떤 알고리즘을 사용했는지
        log.debug("디버그 : " + jwtToken);
        // JWT 토큰 응답
        customJWTResponse(jwtToken, userPS, resp);

        // chain.doFilter(req, resp);
    }

    private void customResponse(String msg, HttpServletResponse resp) throws IOException, JsonProcessingException {
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        resp.setStatus(400);
        ResponseDto<?> responseDto = new ResponseDto<>(-1, msg, null);
        ObjectMapper om = new ObjectMapper();
        String body = om.writeValueAsString(responseDto);
        out.println(body);
        out.flush();
    }
    private void customJWTResponse(String token, User userPS, HttpServletResponse resp) throws IOException, JsonProcessingException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setHeader("Authorization", "Bearer " + token); // "Bearer " 공백 꼭 확인하기 공백 필수
        PrintWriter out = resp.getWriter();
        resp.setStatus(200);
        ResponseDto<?> responseDto = new ResponseDto<>(1, "성공", new SessionUser(userPS));
        ObjectMapper om = new ObjectMapper();
        String body = om.writeValueAsString(responseDto);
        out.println(body);
        out.flush();
    }

}