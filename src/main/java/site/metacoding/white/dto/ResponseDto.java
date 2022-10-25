package site.metacoding.white.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto <R>{
    private Integer code;
    private String msg;
    private R data;


    

}
