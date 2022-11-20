package ru.stupakov.insidemessages.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.stupakov.insidemessages.api.request.AuthenticationRequest;
import ru.stupakov.insidemessages.security.AuthDetails;
import ru.stupakov.insidemessages.security.JWTUtil;
import ru.stupakov.insidemessages.servicies.AuthDetailsService;
import ru.stupakov.insidemessages.utils.exceptions.UserErrorResponse;
import ru.stupakov.insidemessages.utils.exceptions.UserNotFoundExceptions;

import java.util.Map;

/**
 * @author Stupakov D. L.
 **/
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final AuthDetailsService authDetailsService;


    /**
     * Post запрос которые проверяет учетные записи пользователя, и если такой пользователь существует - отправляет ему jwtToken
     * Тип запроса: POST
     * Url: http://localhost:8080/auth/login
     * @param authenticationRequest
     * тело запроса:
     * body{
     *     "name": <Имя пользователя>,
     *     "password" : <Пароль>
     * }
     * @return
     * Формат ответа:
     *{
     *     "token": <сгенерированный для пользователя jwt token>
     * }
     * Срок токена 2 часа
     */
    @PostMapping("/login")
    public Map<String, String> getJWTToken(@RequestBody AuthenticationRequest authenticationRequest){
        AuthDetails authDetails = (AuthDetails) authDetailsService.loadUserByUsername(authenticationRequest.getName());
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authDetails.getUsername(), authDetails.getPassword());
        authenticationManager.authenticate(authInputToken);
        return Map.of("token", jwtUtil.generateToken(authenticationRequest.getName()));
    }

    /**
     * Обрабатывает исключение BadCredentialsException
     * @param exception
     * @return Возвращаем сообщение об ошибке и время, когда она произошла
     */
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(BadCredentialsException exception){

        UserErrorResponse userErrorResponse = new UserErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        //в ответе будет тело HTTP ответа (response) и статус в заголовке
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
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


}
