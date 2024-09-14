package org.blogblog.userservice.config.security;

import org.springframework.stereotype.Component;

@Component("AuthoritiesConstants")
public final class AuthoritiesConstants {
    public final static String ANONYMOUS = "ROLE_ANONYMOUS";
}
