package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;
@Getter
@Setter
public class UserRequestDto {

    @Getter
    @Setter
    public static class UserJoinReqDto { // 인증관련 로직들은 전부다 앞에 엔티티 안붙임. POST /user -> /join
        private String username;
        private String password;

        public User toEntity() {
            return User.builder().username(this.username).password(this.password).build();
        }

    }

    @Setter
    @Getter
    public static class UserLoginReqDto {
        private String username;
        private String password;
    }
    @Setter
    @Getter
    public static class UserUpdateReqDto {
        private String username;
        private String password;
        private SessionUser sessionUser;
        
        public User toEntity() {
            return User.builder().id(sessionUser.getId()).username(username).password(password).build();
        }
    }
    
}
