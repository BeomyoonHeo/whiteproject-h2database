package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer qty;
    private String mcp; //제조사
}

class ProductDto {
    private String name;
    private Integer qty;
    private Integer price;
}

public class MapperTest {
    
    //1. Product 객체생성(디폴트)
    //2. 값 넣기
    //3. ProductDto객체생성(디폴트)
    //4. Product -> ProductDto로 옮기기
    
}
