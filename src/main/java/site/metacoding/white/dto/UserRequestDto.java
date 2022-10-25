package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;
@Getter
@Setter
public class UserRequestDto {

    @Getter
    @Setter
    public static class JoinReqDto { // 인증관련 로직들은 전부다 앞에 엔티티 안붙임. POST /user -> /join
        private String username;
        private String password;

        public User toEntity() {
            return User.builder().username(this.username).password(this.password).build();
        }

    }
    @Setter
    @Getter
    public static class LoginReqDto {
        private String username;
        private String password;
    }
    
}
