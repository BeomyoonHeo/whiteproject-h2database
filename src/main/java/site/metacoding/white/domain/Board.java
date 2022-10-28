package site.metacoding.white.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

    // 조회를 위해서만 필요함
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY) // mappedBy옵션을 사용해서 테이블 생성을 막는다. - Lazy 전략을 사용
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(Long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
    

}
