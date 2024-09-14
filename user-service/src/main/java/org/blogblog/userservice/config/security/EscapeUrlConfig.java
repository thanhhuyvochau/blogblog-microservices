package org.blogblog.userservice.config.security;

import lombok.Getter;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

public class EscapeUrlConfig {
    @Getter
    private static final List<EscapeUrl> escapeUrls = new ArrayList<>();

    static {
        escapeUrls.add(new EscapeUrl("/auth/register", "POST"));
        escapeUrls.add(new EscapeUrl("api-docs/swagger-config", "GET"));
        escapeUrls.add(new EscapeUrl("/swagger-ui/**", "GET"));
        escapeUrls.add(new EscapeUrl("api-docs", "GET"));
        escapeUrls.add(new EscapeUrl("/v3/api-docs/**", "GET"));
    }

    @Getter
    public static class EscapeUrl {
        private final String url;
        private final String method;

        public EscapeUrl(String url, String method) {
            this.url = url;
            this.method = method;
        }

    }

    /**
     * This method is used in case we want to control user route by own function (custom)
     */
    public static boolean shouldBypassAuthentication(AntPathMatcher pathMatcher, String requestUri, String requestMethod) {
        return EscapeUrlConfig.getEscapeUrls().stream()
                .anyMatch(escapeUrl -> pathMatcher.match(escapeUrl.getUrl(), requestUri) && escapeUrl.getMethod().equalsIgnoreCase(requestMethod));
    }
}
