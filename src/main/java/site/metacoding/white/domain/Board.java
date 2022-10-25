package site.metacoding.white.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board {
    @Id// - primary key 적용
    @GeneratedValue(strategy=GenerationType.IDENTITY)// - auto increment 적용 / 해당 DB의 Identity전략을 그대로 따라간다.
    private Long id;
    private String title;
    @Column(length=1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // foreign key 생성
    private User user;

    @Builder
    public Board(Long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
    

}
