package ru.stupakov.insidemessages.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Stupakov D. L.
 **/
@Data
@AllArgsConstructor
public class AuthenticationRequest {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should br between 2 to 30")
    private String name;
    private String password;
}
