package chat.repositories;

import chat.models.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryJpaImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    @Transactional
    public Long save(User entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u where u.username = :username", User.class)
                .setParameter("username", username).getResultList().stream().findFirst();
    }
}
