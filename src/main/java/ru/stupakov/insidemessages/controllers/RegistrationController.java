package ru.stupakov.insidemessages.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.stupakov.insidemessages.api.request.AuthenticationRequest;
import ru.stupakov.insidemessages.api.request.RegistrationRequest;
import ru.stupakov.insidemessages.security.AuthDetails;
import ru.stupakov.insidemessages.servicies.RegistrationService;
import ru.stupakov.insidemessages.utils.exceptions.UserAlreadyExistException;
import ru.stupakov.insidemessages.utils.exceptions.UserErrorResponse;
import ru.stupakov.insidemessages.utils.exceptions.UserNotFoundExceptions;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * @author Stupakov D. L.
 **/
@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    /**
     * Post запрос для регистрации нового пользователя
     * Тип запроса: POST
     * Url: http://localhost:8080/registration
     * @param registrationRequest
     * тело запроса:
     * body{
     *     "name": <Имя пользователя>,
     *     "password" : <Пароль>
     * }
     * @return
     * Отправляет статус запроса 200 при успешном выполнении
     */
    @PostMapping()
    public HttpStatus registration(@RequestBody RegistrationRequest registrationRequest){
        registrationService.register(registrationRequest);
        return HttpStatus.OK;
    }

    /**
     * Обрабатывает исключение UserAlreadyExistException
     * @param exception (exception возникает, когда такой пользователь уже существует в базе)
     * @return Возвращаем сообщение об ошибке и время, когда она произошла
     */
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserAlreadyExistException exception){
        UserErrorResponse userErrorResponse = new UserErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
