package ru.stupakov.insidemessages.servicies;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import ru.stupakov.insidemessages.InsideMessagesApplication;
import ru.stupakov.insidemessages.api.request.UserMessageRequest;
import ru.stupakov.insidemessages.models.User;
import ru.stupakov.insidemessages.repositories.MessageRepository;
import ru.stupakov.insidemessages.repositories.UserRepository;
import ru.stupakov.insidemessages.security.AuthDetails;
import ru.stupakov.insidemessages.security.JWTUtil;
import ru.stupakov.insidemessages.utils.exceptions.UserNotFoundExceptions;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Stupakov D. L.
 **/
@SpringBootTest(
        classes = InsideMessagesApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class MessageServiceTest {


    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    /*
        Тестируем UserNotFoundExceptions
     */
    @Test
    void notExistUserTest() {
        assertThrows(UserNotFoundExceptions.class, ()->messageService.getUserMessages(new UserMessageRequest("sfasfsad", "dasdas")));
    }

    /*
        Тестируем показ истории если сообщение history 10 и количество сообщений
     */
    @Test
    void showHistoryCountTest() {
        Optional<User> user = userRepository.findByName("user");

        UserDetails userDetails = new AuthDetails(user.get());
        SecurityContextHolder.setContext(new SecurityContextImpl(
                new UsernamePasswordAuthenticationToken(userDetails, null)));

        for (int i = 0; i < 15; i++) {
            messageService.save(new UserMessageRequest("user", "test message"));
        }
        int expected = 10;
        int actual = messageService.getUserMessages(new UserMessageRequest("user", "history 10")).size();
        messageRepository.deleteAll();
        assertEquals(expected,actual);

    }

    /*
        тестируем сохранение сообщения пользователя
     */
    @Test
    void saveMessageTest() {
        Optional<User> user = userRepository.findByName("user");

        UserDetails userDetails = new AuthDetails(user.get());
        SecurityContextHolder.setContext(new SecurityContextImpl(
                new UsernamePasswordAuthenticationToken(userDetails, null)));

        messageService.save(new UserMessageRequest("user", "test message"));
        String expected = messageService.getUserMessages(new UserMessageRequest("user", "history 10")).get(0).getMessage();
        String actual = "test message";
        messageRepository.deleteAll();
        assertEquals(expected, actual);
    }



}