package co.test.testpro.repository;

import co.test.testpro.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findByUserId(Long id) {
        User member = em.find(User.class,id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> result = em.createQuery("select u from User as u where u.username = :username",User.class)
                .setParameter("username",username)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User as u",User.class)
                .getResultList();
    }
}
