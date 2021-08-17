package co.test.testpro.repository;

import co.test.testpro.domain.User;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    User save(User user);
    Optional<User> findByUserId(Long id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
}
