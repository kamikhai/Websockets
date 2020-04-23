package chat.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<ID, V> {
    Optional<V> find(ID id);
    List<V> findAll();
    Long save(V entity);
    boolean delete(ID id);
}
