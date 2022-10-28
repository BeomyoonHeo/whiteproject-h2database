package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

public class UserResponseDto {

    @Getter
    @Setter
    public static class UserJoinRespDto {
        private Long id;
        private String username;

        // 응답의 DTO는 생성자로 처리한다.
        public UserJoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }

    }

    @Getter
    @Setter
    public static class UserUpdateRespDto {
        private String username;
        private String password;
        
        public UserUpdateRespDto(User user) {
            this.username = user.getUsername();
            this.password = user.getPassword();
        }

        @Getter
        @Setter
        public static class UserDetailRespDto {
            private String username;
            
            public UserDetailRespDto(User user) {
                this.username = user.getUsername();
            }
        }

        
    }
    
}
