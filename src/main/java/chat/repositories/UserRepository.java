package chat.repositories;

import chat.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Long, User> {
    Optional<User> findUserByUsername(String username);
}
