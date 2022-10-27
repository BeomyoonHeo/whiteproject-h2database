package site.metacoding.white.domain;

import java.util.Optional;

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
    
    public Optional<Comment> findById(Long id) {
        try {
            Optional<Comment> CommentOP = Optional.of(em
            .createQuery("select c from Comment c where c.id = :id", Comment.class)
            .setParameter("id", id)
            .getSingleResult());
            return CommentOP;
                } catch (Exception e) {
                    return Optional.empty();
        }
    };

    public void deleteById(Long id) {
        em.createQuery("delete from Comment c where c.id = :id ")
            .setParameter("id", id)
            .executeUpdate();
    }

}
