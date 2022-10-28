package site.metacoding.white.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import site.metacoding.white.dto.UserRequestDto.JoinReqDto;
import site.metacoding.white.dto.UserRequestDto.LoginReqDto;
import site.metacoding.white.service.UserService;

@ActiveProfiles("test")
// 테스트에서 중요한것은 테스트 격리이다. - 테스트 후에 다시 테스트를 진행할 때 같은 값으로 테스트 진행시에 테스트에 이상이 없어야 한다.
//@Transactional//테스트 트랜잭션 진행 후 자동으로 롤백
@Sql("classpath:truncate.sql") // truncate.sql은 자동으로 table을 empty상태로 만들어 준다. - 내부 메서드가 종료되면 자동으로 실행 해준다.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //Spring Boot 통합테스트 진행시 자동으로 롤백 해주지 않는다. - @Transactional이 먹히지 않는다.
public class UserApiControllerTest {

    @Autowired
    private TestRestTemplate rt; // 통신시 사용하는 라이브러리

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserService userService;

    private static HttpHeaders headers;

    @BeforeAll // 선언시 static으로 선언해야한다. - container에 띄우기 위해 사용한다.
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    
    
    //@Order(1)
    @Test
    public void join_test() throws JsonProcessingException {
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("very");
        joinReqDto.setPassword("1234");

        String body = om.writeValueAsString(joinReqDto);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/join", HttpMethod.POST,
                request, String.class);

        // then
        // System.out.println(response.getStatusCode());
        // System.out.println(response.getBody());

        DocumentContext dc = JsonPath.parse(response.getBody());
        // System.out.println(dc.jsonString());
        Integer code = dc.read("$.code");
        Assertions.assertThat(code).isEqualTo(1);
    }

    @Test
    public void login_test() throws JsonProcessingException {
        //data init
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("very");
        joinReqDto.setPassword("1234");
        userService.save(joinReqDto); // join_test메서드에서 검증이 이미 끝났기 때문에 굳이 return값을 받아서 테스트를 진행 할 필요가 없다.


        // given
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("very");
        loginReqDto.setPassword("1234");
        String body = om.writeValueAsString(loginReqDto);
        System.out.println("디버그: " + body);

        //when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/login", HttpMethod.POST,
                request, String.class);

        System.out.println("디버그 : " + response.getBody());

        //then
        DocumentContext dc = JsonPath.parse(response.getBody());
        // System.out.println(dc.jsonString());
        Integer code = dc.read("$.code");
        String username = dc.read("$.data.username");
        String msg = dc.read("$.msg");
        Integer id = dc.read("$.data.id");
        Assertions.assertThat(code).isEqualTo(1);
        Assertions.assertThat(username).isEqualTo("very");
        Assertions.assertThat(msg).isEqualTo("ok");
        Assertions.assertThat(id).isEqualTo(1);

    }

    // //@Order(2)
    // @Test
    // public void join_test2() throws JsonProcessingException {
    //     // given
    //     JoinReqDto joinReqDto = new JoinReqDto();
    //     joinReqDto.setUsername("very");
    //     joinReqDto.setPassword("1234");

    //     String body = om.writeValueAsString(joinReqDto);
    //     System.out.println(body);

    //     // when
    //     HttpEntity<String> request = new HttpEntity<>(body, headers);
    //     ResponseEntity<String> response = rt.exchange("/join", HttpMethod.POST,
    //             request, String.class);

    //     // then
    //     // System.out.println(response.getStatusCode());
    //     // System.out.println(response.getBody());

    //     DocumentContext dc = JsonPath.parse(response.getBody());
    //     // System.out.println(dc.jsonString());
    //     Integer code = dc.read("$.code");
    //     Assertions.assertThat(code).isEqualTo(1);
    // }
}
