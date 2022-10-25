package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository // IOC 등록
public class UserRepository {
    //DI
    private final EntityManager em;

    public User save(User user) {
        // persistence context에 영속화 시키기 -> 자동flush(트랜잭션 종료시)
        // flush가 종료되면 자동으로 DB데이터와 동기화를 시켜준다.
        log.debug("디버그: "+user.getId());// 영속화 전
        em.persist(user); // insert
        log.debug("디버그: "+user.getId());// 영속화 후 (DB와 동기화된다. - 커밋 전)
        return user;

    }

    public User findByUsername(String username) {
        return em.createQuery("select u from User u where u.username = :username", User.class)
            .setParameter("username", username)
            .getSingleResult();
        
    }

    public User findById(Long id) {
        return em.createQuery("select u from User u where u.id = :id",User.class).setParameter("id", id).getSingleResult();

    }

}
