package ru.stupakov.insidemessages.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.stupakov.insidemessages.api.request.UserMessageRequest;
import ru.stupakov.insidemessages.api.response.UserMessagesResponse;
import ru.stupakov.insidemessages.security.AuthDetails;
import ru.stupakov.insidemessages.servicies.MessageService;
import ru.stupakov.insidemessages.utils.exceptions.TokenNotBelongUserException;
import ru.stupakov.insidemessages.utils.exceptions.UserErrorResponse;
import ru.stupakov.insidemessages.utils.exceptions.UserNotFoundExceptions;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Stupakov D. L.
 **/
@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    /**
     * Запрос, который добавляет сообщение пользователя в базу:

     *
     * Необходимо чтобы в запросе был заголовок:
     * <B>Authorization - со значением Bearer_<Актуальный JWT токен></B>
     *
     * Тип запроса: POST
     * @param userMessageRequest -
     * Тело запроса:
     * {
     *     "name" : <Имя пользователя>,
     *     "message" : <Сообщение: либо history 10, либо любое другое>
     * }
     * @return
     *  1. Если в теле запроса message = "history 10" - Возвращает последние 10 сообщение пользователя (в теле запроса name)
     *  2. Иначе добавляет новое сообщения пользователя
     *  3. Если пользователя не существует, выбрасывается исключения и в ответ приходит ошибка
     *
     */
    @PostMapping("/message")
    public List<UserMessagesResponse> sendMessageOrGetHistory(@RequestBody @Valid UserMessageRequest userMessageRequest){
            if (userMessageRequest.getMessage().equals("history 10")){
                return messageService.getUserMessages(userMessageRequest);
            } else {
                messageService.save(userMessageRequest);
                return null;
            }
    }

    /**
     * Обрабатывает исключение UserNotFoundExceptions
     * @param exception
     * @return Возвращаем сообщение об ошибке и время, когда она произошла
     */
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundExceptions exception){
        UserErrorResponse userErrorResponse = new UserErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        //в ответе будет тело HTTP ответа (response) и статус в загаловке
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение TokenNotBelongUserException
     * @param exception
     * @return Возвращаем сообщение об ошибке и время, когда она произошла
     */
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(TokenNotBelongUserException exception){
        UserErrorResponse userErrorResponse = new UserErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }


}
