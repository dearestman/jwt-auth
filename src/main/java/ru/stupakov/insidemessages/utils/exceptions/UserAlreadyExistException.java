package ru.stupakov.insidemessages.utils.exceptions;

/**
 * @author Stupakov D. L.
 **/
public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
