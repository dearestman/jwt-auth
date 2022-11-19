package ru.stupakov.insidemessages.servicies;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.stupakov.insidemessages.api.request.UserMessageRequest;
import ru.stupakov.insidemessages.api.response.UserMessagesResponse;
import ru.stupakov.insidemessages.models.Message;
import ru.stupakov.insidemessages.models.User;
import ru.stupakov.insidemessages.repositories.MessageRepository;
import ru.stupakov.insidemessages.repositories.UserRepository;
import ru.stupakov.insidemessages.security.AuthDetails;
import ru.stupakov.insidemessages.utils.exceptions.TokenNotBelongUserException;
import ru.stupakov.insidemessages.utils.exceptions.UserNotFoundExceptions;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Stupakov D. L.
 **/
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    /*
        Проверяет, принадлежит ли токен пользователю
     */
    public void mapTokenForUser(String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();

        if (!username.equals(authDetails.getUsername())) throw new TokenNotBelongUserException("Invalid token");
    }


    /*
        Получаем 10 последних сообщений пользователя
     */
    public List<UserMessagesResponse> getUserMessages(UserMessageRequest userMessageRequest){

        Optional<User> user = userRepository.findByName(userMessageRequest.getName());
        if (user.isEmpty()) throw new UserNotFoundExceptions("User not found");
        mapTokenForUser(user.get().getName());

        return messageRepository.findTop10ByUserIdOrderByCreatedAtDesc(user.get().getId())
                .stream()
                .map(this::convertToUserMessageResponse)
                .collect(Collectors.toList());
    }

    /*
        Переводим сущность Message в DTO UserMessagesResponse
     */
    private UserMessagesResponse convertToUserMessageResponse(Message message) {
        return modelMapper.map(message, UserMessagesResponse.class);
    }

    /*
        Сохраняем сообщение пользователя в базу данных
     */
    @Transactional
    public void save(UserMessageRequest userMessageRequest){

        messageRepository.save(convertToMessage(userMessageRequest));

    }

    /*
        Преобразуем DTO в сущность Message и проверка сопоставление токена и пользователя
     */
    private Message convertToMessage(UserMessageRequest userMessageRequest) {
        Message message = modelMapper.map(userMessageRequest, Message.class);
        Optional<User> user = userRepository.findByName(userMessageRequest.getName());

        if (user.isEmpty()) throw new UserNotFoundExceptions("User not found");
        mapTokenForUser(user.get().getName());

        message.setUser(user.get());
        message.setCreatedAt(LocalDateTime.now());
        return message;
    }
}
