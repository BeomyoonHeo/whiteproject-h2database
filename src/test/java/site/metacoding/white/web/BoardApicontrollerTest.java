package site.metacoding.white.web;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.Comment;
import site.metacoding.white.domain.CommentRepository;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.BoardRequestDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardRequestDto.BoardUpdateReqDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.util.SHA256;


@ActiveProfiles("test")
@Sql("classpath:truncate.sql")
@Transactional // 트랜잭션 안붙이면 영속성 컨텍스트에서 DB로 flush 안됨 (Hibernate 사용시)
@AutoConfigureMockMvc // MockMvc Ioc 컨테이너에 등록
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // 가짜 환경으로 실행
public class BoardApicontrollerTest {

    private static final String APPLICATION_JSON = "application/json; charset=utf-8";
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SHA256 sha256;

    private MockHttpSession session;

    private static HttpHeaders headers;

    // @BeforeAll // 최초에 한번만 실행
    // public static void init() { //이름 통일해주기 init
    //     headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON);
    // }

    @BeforeEach// test메서드 진입전에 트랜잭션 발동
    public void sessionInit() {
        session = new MockHttpSession();
        User user = User.builder().id(1L).username("ssar").build();
        session.setAttribute("principal", new SessionUser(user));
    }

    @BeforeEach
    public void dataInit() {
        String encPassword = sha256.encrypt("1234");
        User user = User.builder().username("ssar").password(encPassword).build();
        User userPS = userRepository.save(user);

        Board board = Board.builder()
                .title("스프링1강")
                .content("트랜잭션관리")
                .user(userPS)
                .build();
        Board boardPS = boardRepository.save(board);

        Comment comment = Comment.builder()
                .content("내용좋아요")
                .board(boardPS)
                .user(userPS)
                .build();


        commentRepository.save(comment);
    }
@Test
public void save_test() throws Exception {
    // given
    BoardSaveReqDto boardSaveReqDto = new BoardSaveReqDto();
    boardSaveReqDto.setTitle("스프링1강");
    boardSaveReqDto.setContent("트랜잭션관리");

    String body = om.writeValueAsString(boardSaveReqDto);

    // when
    ResultActions resultActions = mvc
            .perform(post("/board").content(body)
                    .contentType("application/json; charset=utf-8").accept("application/json; charset=utf-8")
                    .session(session));

    // then
    MvcResult mvcResult = resultActions.andReturn();
    System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
}

    @Test
    public void findById_test() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions resultActions = mvc
                .perform(get("/board/" + id).accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        // BoardDetailRespDto boardDetailRespDto = om.readValue(mvcResult.getResponse().getContentAsString(),
        //         BoardDetailRespDto.class);
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.title").value("스프링1강"));
    }

    @Test
    public void findAll_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mvc
                .perform(get("/board").accept(APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        // BoardDetailRespDto boardDetailRespDto = om.readValue(mvcResult.getResponse().getContentAsString(),
        //         BoardDetailRespDto.class);
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(jsonPath("$.data.[0].title").value("스프링1강"));
    }

    @Test
    public void update_test() throws Exception {
        // given
        Long id = 1L;
        BoardUpdateReqDto boardUpdateReqDto = new BoardUpdateReqDto();
        boardUpdateReqDto.setId(id);
        boardUpdateReqDto.setTitle("스프링2강");
        boardUpdateReqDto.setContent("JUNIT공부");

        String body = om.writeValueAsString(boardUpdateReqDto);

        // when
        ResultActions resultActions = mvc
                .perform(put("/board/" + id).content(body)
                        .contentType(APPLICATION_JSON).accept(APPLICATION_JSON)
                        .session(session));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        resultActions.andExpect(jsonPath("$.data.title").value("스프링2강"));
    }
    
    @Test
    //@Commit
    public void deleteById_test() throws Exception {
        // given
        Long id = 1L;

        // when
        ResultActions resultActions = mvc
                .perform(delete("/board/"+ id)
                        .accept(APPLICATION_JSON)
                        .session(session));

        // then
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
        //resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("스프링1강"));
    }



}
