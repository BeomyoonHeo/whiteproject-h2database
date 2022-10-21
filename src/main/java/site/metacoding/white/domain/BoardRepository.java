package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    
    private final EntityManager em;

    public void save(Board board) {
        em.persist(board); // insert

    }
    
    public Board findById(Long id) {
        Board boardPS = 
        em.createQuery("select b from Board b where b.id = :id", Board.class)
            .setParameter("id", id)
            .getSingleResult(); //entity 조회 쿼리 - JPQL
        return boardPS;
    }
}
