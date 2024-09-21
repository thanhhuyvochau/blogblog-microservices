package org.blogblog.userservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SignInRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Tài khoản")
    private String email;

    @Schema(description = "Mật khẩu")
    private String password;
}
