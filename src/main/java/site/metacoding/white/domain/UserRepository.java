package site.metacoding.white.domain;

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

    public User findByUsername(String username) {
        return em.createQuery("select u from User u where u.username = :username", User.class)
            .setParameter("username", username)
            .getSingleResult();
        
    }

}
