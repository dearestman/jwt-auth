package ru.stupakov.insidemessages.servicies;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import ru.stupakov.insidemessages.IntegrationTestBase;
import ru.stupakov.insidemessages.models.User;
import ru.stupakov.insidemessages.security.AuthDetails;
import ru.stupakov.insidemessages.utils.exceptions.UserNotFoundExceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Stupakov D. L.
 **/
//
class AuthDetailsServiceTest extends IntegrationTestBase {
    @Autowired
    private AuthDetailsService authDetailsService;


    /*
        Тестируем throw UserNotFoundExceptions;
     */
    @Test
    public void notExistUserTest(){
        assertThrows(UserNotFoundExceptions.class, ()->authDetailsService.loadUserByUsername("dsadassda"));
    }

    /*
        Тестируем аутентификацию
     */
    @Test
    public void existUserTest(){

        User user = new User();
        user.setId(1);
        user.setName("user");
        user.setPassword("123");
        user.setRole("ROLE_USER");
        UserDetails expected = new AuthDetails(user);

        UserDetails actual = authDetailsService.loadUserByUsername("user");

        assertEquals(expected.getUsername(), actual.getUsername());
    }



}