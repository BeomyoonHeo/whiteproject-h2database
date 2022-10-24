package site.metacoding.white.domain;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // IOC 등록
public class UserRepository {
    //DI
    private final EntityManager em;

    public void save(User user) {
        // persistence context에 영속화 시키기 -> 자동flush(트랜잭션 종료시)
        em.persist(user); // insert

    }
}
