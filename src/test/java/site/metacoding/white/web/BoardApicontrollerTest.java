package site.metacoding.white.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.white.domain.User;
import site.metacoding.white.dto.BoardRequestDto.BoardSaveReqDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserRequestDto.UserJoinReqDto;
import site.metacoding.white.service.BoardService;
import site.metacoding.white.service.UserService;


//@ToString// - 해당 객체의 필드값을 String형태로 보여준다.
@ActiveProfiles("test")// application-test.yml로 동작
@Sql("classpath:truncate.sql")
@AutoConfigureMockMvc // MockMvc ioc 컨테이너에 등록
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // IOC 띄워주기
public class BoardApicontrollerTest {
    
    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MockMvc mvc;

    private MockHttpSession session;
    private MockHttpServletRequest request;

    private static HttpHeaders headers;

    @BeforeAll
    public static void init() { // 이름 통일해주기
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void findById_test() throws Exception {
        //data init
        UserJoinReqDto joinReqDto = new UserJoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        userService.save(joinReqDto);

        BoardSaveReqDto boardSaveReqDto = new BoardSaveReqDto();
        boardSaveReqDto.setTitle("테스트타이틀1");
        boardSaveReqDto.setContent("테스트컨텐트");
        User user = User.builder().id(1L).username("ssar").build();
        boardSaveReqDto.setSessionUser(new SessionUser(user));
        boardService.save(boardSaveReqDto);        

        //given
        Long id = 1L;
        
        //when
        ResultActions resultActions = mvc.perform(get("/board/" + id).accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void save_test() throws JsonProcessingException {
        
        UserJoinReqDto joinReqDto = new UserJoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        userService.save(joinReqDto);

        session = new MockHttpSession();
        request = new MockHttpServletRequest();

        User user = User.builder().id(1L).username("ssar").build();
        SessionUser sessionUser = new SessionUser(user);

        session.setAttribute("principal", sessionUser);

        request.setSession(session);

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    
        //given
        BoardSaveReqDto boardSaveReqDto = new BoardSaveReqDto();
        boardSaveReqDto.setTitle("스프링1강");
        boardSaveReqDto.setContent("트랜잭션관리");

        String body = om.writeValueAsString(boardSaveReqDto);
        System.out.println("디버그 : " + body);

        //when

        
        //then

    }
}
