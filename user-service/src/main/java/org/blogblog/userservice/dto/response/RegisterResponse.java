package org.blogblog.userservice.dto.response;

import lombok.Data;

@Data
public class RegisterResponse {
    private String firstName;
    private String lastName;
    private String email;
}
