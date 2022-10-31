package site.metacoding.white.config.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.dto.ResponseDto;

@Slf4j
public class JwtAuthenticationFilter implements Filter {
    // /login 요청시
    // post요청시
    // username, password(json)
    //db확인
    //토큰 생성
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) // 제일 순수한 request 와 response
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        if (!req.getMethod().equals("POST")) {
            resp.setStatus(400);
            ResponseDto<?> responseDto = new ResponseDto<>(-1, "로그인시에는 post요청을 해야합니다.", null);
            ObjectMapper om = new ObjectMapper();
            String body = om.writeValueAsString(responseDto);
            out.println(body); // 스프링전에 throw를 던지면 controlladvice가 못 낚아챈다.
            out.flush();
            return;
        }
        //chain.doFilter(req, resp); // dofilter를 통해 dispatcherservlet으로 전달 
        
    }
}
