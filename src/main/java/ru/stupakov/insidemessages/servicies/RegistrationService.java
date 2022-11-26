package ru.stupakov.insidemessages.servicies;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stupakov.insidemessages.api.request.RegistrationRequest;
import ru.stupakov.insidemessages.models.User;
import ru.stupakov.insidemessages.repositories.UserRepository;
import ru.stupakov.insidemessages.utils.exceptions.UserAlreadyExistException;

import java.util.Optional;

/**
 * @author Stupakov D. L.
 **/
@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;



    @Transactional
    public void register(RegistrationRequest registrationRequest){
        Optional<User> user = userRepository.findByName(registrationRequest.getName());
        if (user.isPresent()) throw new UserAlreadyExistException("User already exist");
        userRepository.save(convertToUser(registrationRequest));
    }

    private User convertToUser(RegistrationRequest registrationRequest) {
        User user = modelMapper.map(registrationRequest, User.class);
        user.setRole("ROLE_USER");
        return user;
    }

}
