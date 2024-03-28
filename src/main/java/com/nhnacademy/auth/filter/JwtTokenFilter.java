package com.nhnacademy.auth.filter;

import com.nhnacademy.auth.properties.JwtProperties;
import com.nhnacademy.auth.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtProperties jwtProperties;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, JwtProperties jwtProperties) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.isNull(authorizationToken) || !authorizationToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token;

        try {
            token = authorizationToken.split(" ")[1];
        }catch (Exception e) {
            log.error("token 추출에 실패하였습니다.", e);
            filterChain.doFilter(request, response);
            return;
        }

        if (JwtTokenFilter.isExpired(token, jwtProperties.getSecret())) {

        }
    }

    private static boolean isExpired(String token, String secret) {
        Date expiredTime = extractClaims(token, secret).getExpiration();
    }

    private static Claims extractClaims(String token, String secret) {
    }
}
