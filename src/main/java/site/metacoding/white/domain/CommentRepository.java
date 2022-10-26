package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public Comment save(Comment comment) {
        
        em.persist(comment); // insert

        return comment;
    }

    public void deleteById(Long id) {
        em.createQuery("delete from Comment c where c.id = :id ").setParameter("id", id).executeUpdate();
    };
}
