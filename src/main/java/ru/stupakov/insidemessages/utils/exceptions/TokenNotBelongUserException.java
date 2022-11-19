package ru.stupakov.insidemessages.utils.exceptions;

/**
 * @author Stupakov D. L.
 **/
public class TokenNotBelongUserException extends RuntimeException{
    public TokenNotBelongUserException(String message) {
        super(message);
    }
}
