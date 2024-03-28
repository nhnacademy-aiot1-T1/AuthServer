package com.nhnacademy.auth.filter;

import com.nhnacademy.auth.domain.User;
import com.nhnacademy.auth.properties.JwtProperties;
import com.nhnacademy.auth.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final String secret;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, String secret) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.secret = secret;
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

        if (JwtTokenFilter.isExpired(token, secret)) { //TODO access token의 유효 기간이 만료되었을 때. refresh token이 추가되면 코드 수정.
            filterChain.doFilter(request, response);
            return;
        }

        String userId = extractClaims(token, secret).get("userId").toString();
        User user =

        UsernamePasswordAuthenticationFilter authenticationFilter = new UsernamePasswordAuthenticationFilter(
                user.getUserId(), null, List.of(new SimpleGrantedAuthority(user.getUserRole().name())));

        authenticationFilter.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    }

    private static boolean isExpired(String token, String secret) {
        Date expiredTime = extractClaims(token, secret).getExpiration();
        return expiredTime.before(new Date());
    }

    private static Claims extractClaims(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
