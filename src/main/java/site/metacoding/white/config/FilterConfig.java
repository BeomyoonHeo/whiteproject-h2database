package site.metacoding.white.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.config.auth.JwtAuthenticationFilter;
import site.metacoding.white.config.auth.JwtAuthorizationFilter;
import site.metacoding.white.domain.UserRepository;

@Slf4j
@Profile("dev") // yml파일이 dev모드일때만 작동하도록 설정
@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final UserRepository userRepository; // DI (스프링 IOC container)

    //Ioc등록
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterRegister() {
        log.debug("디버그 : 인증 필터 등록");
        FilterRegistrationBean<JwtAuthenticationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthenticationFilter(userRepository));
        bean.addUrlPatterns("/login");
        bean.setOrder(1); // filter의 실행 순서는 알 수 없다. - 순서를 정해줘야 함 낮은 순서대로 실행
        return bean;
    }
    
    @Bean
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilterRegister() {
        log.debug("디버그 : 인가 필터 등록");
        FilterRegistrationBean<JwtAuthorizationFilter> bean = new FilterRegistrationBean<>(new JwtAuthorizationFilter());
        bean.addUrlPatterns("/s/*"); // 원래 두개인데, 이친구만 예외
        bean.setOrder(2);
        return bean;
    }
}
