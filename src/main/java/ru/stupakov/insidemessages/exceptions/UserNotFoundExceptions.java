package ru.stupakov.insidemessages.exceptions;

/**
 * @author Stupakov D. L.
 **/
public class UserNotFoundExceptions extends RuntimeException{
    public UserNotFoundExceptions(String message) {
        super(message);
    }
}
