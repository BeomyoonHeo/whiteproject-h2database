package site.metacoding.white.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    //User 누가 썻는지
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    //Board 어디에 썻는지
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
