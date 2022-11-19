package ru.stupakov.insidemessages.utils.exceptions;

/**
 * @author Stupakov D. L.
 **/
public class UserNotFoundExceptions extends RuntimeException{
    public UserNotFoundExceptions(String message) {
        super(message);
    }
}
