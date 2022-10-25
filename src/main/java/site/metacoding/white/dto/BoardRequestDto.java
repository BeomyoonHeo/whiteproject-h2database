package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;


public class BoardRequestDto {

    @Getter
    @Setter
    public static class BoardSaveReqDto {
        private String title;
        private String content;

        private ServiceDto serviceDto;

        // 클라이언트한테 받는게 아님
        public void newInstance() {
            this.serviceDto = new ServiceDto();
        }

        @Getter
        @Setter
        public class ServiceDto {
            private User user;

        }
    }
    // DTO는 여기다가 추가로
}
