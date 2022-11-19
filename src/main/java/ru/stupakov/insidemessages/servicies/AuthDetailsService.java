package ru.stupakov.insidemessages.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.stupakov.insidemessages.utils.exceptions.UserNotFoundExceptions;
import ru.stupakov.insidemessages.models.User;
import ru.stupakov.insidemessages.repositories.UserRepository;
import ru.stupakov.insidemessages.security.AuthDetails;

import java.util.Optional;

/**
 * @author Stupakov D. L.
 **/
@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isEmpty()){
            throw new UserNotFoundExceptions("User not found");
        }
        return new AuthDetails(user.get());
    }
}
