package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;
@Getter
@Setter
public class UserRequestDto {

    @Getter
    @Setter
    public static class userJoinDto {
        private String username;
        private String password;
        private SaveDto saveDto;


        public void newInstance() {
            this.saveDto = new SaveDto();
        }

        @Getter
        @Setter
        public class SaveDto {
            private User user;
        }
    }
    
}
