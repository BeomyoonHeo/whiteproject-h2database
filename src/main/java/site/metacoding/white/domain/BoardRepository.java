package site.metacoding.white.domain;

import java.util.List;

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
        Board boardPS = em.createQuery("select b from Board b where b.id = :id", Board.class)
                .setParameter("id", id)
                .getSingleResult(); //entity 조회 쿼리 - JPQL
        return boardPS;
    }
    
    public void deleteById(Long id) {
        em.createQuery("delete from Board b where b.id = :id ").setParameter("id", id).executeUpdate();
    };

    public List<Board> findAll() {
        List<Board> boardList = em.createQuery("select b from Board b", Board.class).getResultList();
        return boardList;
    }
}
