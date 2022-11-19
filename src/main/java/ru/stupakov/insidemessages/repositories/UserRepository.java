package ru.stupakov.insidemessages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stupakov.insidemessages.models.User;

import java.util.Optional;

/**
 * @author Stupakov D. L.
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);
}
