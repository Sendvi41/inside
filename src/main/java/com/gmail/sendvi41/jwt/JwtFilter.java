package com.gmail.sendvi41.jwt;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.gmail.sendvi41.constants.ApiConstants.CONTROLLER_TOKEN_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtClientProvider jwtProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain chain) throws IOException, ServletException {
        String tokenFromRequest = getTokenFromRequest(request);
        if (CONTROLLER_TOKEN_PATH.equals(request.getRequestURI())) {
            chain.doFilter(request, response);
        } else if (tokenFromRequest != null && jwtProvider.validateToken(tokenFromRequest)) {
            String userName = jwtProvider.getUserNameFromToken(tokenFromRequest);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            User.withUsername(userName)
                                    .password("")
                                    .authorities("USER")
                                    .build(),
                            null,
                            List.of(new SimpleGrantedAuthority("USER")));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            chain.doFilter(request, response);
        } else {
            throw new JwtException("JWT Token is invalid");
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer_")) {
            return bearer.substring(7);
        }
        return null;
    }
}
