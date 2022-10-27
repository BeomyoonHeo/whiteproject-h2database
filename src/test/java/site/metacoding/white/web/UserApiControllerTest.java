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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import site.metacoding.white.dto.UserRequestDto.JoinReqDto;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {

    @Autowired
    private TestRestTemplate rt; // 통신시 사용하는 라이브러리

    private static ObjectMapper om;
    private static HttpHeaders headers;

    @BeforeAll // 선언시 static으로 선언해야한다. - container에 띄우기 위해 사용한다.
    public static void init() {
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    

    @Test
    public void join_test() throws JsonProcessingException {
        //given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("papa");
        joinReqDto.setPassword("1234");

        String body = om.writeValueAsString(joinReqDto);
        //System.out.println(body);

        //when
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = rt.exchange("/join", HttpMethod.POST, request, String.class);

        //then
        // System.out.println(response.getStatusCode());
        // System.out.println(response.getBody());

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        Assertions.assertThat(code).isEqualTo(1);
    }

}
