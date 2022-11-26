package ru.stupakov.insidemessages.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.stupakov.insidemessages.IntegrationTestBase;
import ru.stupakov.insidemessages.models.User;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author Stupakov D. L.
 **/

public class UserRepositoryTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    /*
           Тестируем поиск пользователя по имени в бд
    */
    @Test
    void testGetByName() {
        Optional<User> user = userRepository.findByName("user");
        assertTrue(user.isPresent());
        user.ifPresent(entity -> {
            assertEquals("user", entity.getName());
        });
    }


    /*
           Тестируем сохранение пользователя в бд
    */
    @Test
    void testSave() {
        User user = new User();
        user.setName("test");
        user.setPassword("testpassword");
        user.setRole("ROLE_USER");
        userRepository.save(user);
        assertNotNull(user.getId());
    }

}
