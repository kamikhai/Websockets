package chat.repositories;

import chat.models.Room;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class RoomsRepositoryJpaImpl implements RoomsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Room> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Room> findAll() {
        return entityManager.createQuery("SELECT r from Room r", Room.class).getResultList();
    }

    @Override
    public Long save(Room entity) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}
