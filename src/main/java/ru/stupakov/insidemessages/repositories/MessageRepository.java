package ru.stupakov.insidemessages.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stupakov.insidemessages.models.Message;

import java.util.List;

/**
 * @author Stupakov D. L.
 **/
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findTop10ByUserIdOrderByCreatedAtDesc(int userId);
}
