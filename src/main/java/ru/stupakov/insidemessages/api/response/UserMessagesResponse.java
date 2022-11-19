package ru.stupakov.insidemessages.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Stupakov D. L.
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessagesResponse {
    private String message;
    private String createdAt;

}
