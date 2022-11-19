package ru.stupakov.insidemessages.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Stupakov D. L.
 **/
@Data
@AllArgsConstructor
public class UserMessagesResponse {
    private String message;
    private String createdAt;

}
