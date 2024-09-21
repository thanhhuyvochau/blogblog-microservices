package org.blogblog.userservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class JwtResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "Access token")
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> permissions = new ArrayList<>();
}
