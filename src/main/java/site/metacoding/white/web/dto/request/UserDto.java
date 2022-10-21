package site.metacoding.white.web.dto.request;

import lombok.Data;
import site.metacoding.white.domain.User;

@Data
public class UserDto {
    private String userName;
    private Integer userNumber;

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setUserName(userName);
        user.setUserNumber(userNumber);
        return user;
    }
}
