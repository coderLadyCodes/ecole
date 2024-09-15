package com.samia.ecole.security;

import com.samia.ecole.entities.Jwt;
import com.samia.ecole.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;

@Service
public class JwtFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final UserService userService;
    private final JwtService jwtService;
    public JwtFilter(HandlerExceptionResolver handlerExceptionResolver, UserService userService, JwtService jwtService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, ServletException {
        String token = null;
        Jwt tokenInDb = null;
        String username = null;
        boolean isTokenExpired = true;
//        try {
//            String authorization = request.getHeader("Authorization");
//            if (authorization == null) {
//                Cookie[] cookies = request.getCookies();
//                if (cookies != null) {
//                    for (Cookie cookie : cookies) {
//                        if ("token".equals((cookie.getName()))) {
//                            authorization = "Bearer " + cookie.getValue();
//                        }
//                    }
//                }
//            }
//            if (authorization != null && authorization.startsWith("Bearer ")) {
//                token = authorization.substring(7);
//                tokenInDb = jwtService.tokenByValue(token);
//                if (tokenInDb.getUser().getEmail().equals(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UserDetails userDetails = userService.loadUserByUsername(username);
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//            }
//
//    } catch(Exception e){
//        handlerExceptionResolver.resolveException(request, response, null, e);
//    }
//     filterChain.doFilter(request, response);
//}
            try {
                String authorization = request.getHeader("Authorization");
                Cookie[] cookies = request.getCookies();
                if(cookies != null && authorization == null){
                    authorization = this.getCookieValue(request, "token");
                }


                if (cookies != null && authorization != null && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);

                tokenInDb = this.jwtService.tokenByValue(token);
                isTokenExpired = jwtService.isTokenExpired(token);
                username = jwtService.extractUsername(token);
                }

                if (!isTokenExpired
                        && tokenInDb.getUser().getEmail().equals(username)

                        && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = userService.loadUserByUsername(username);
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, true, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }

            } catch (Exception exception) {

                handlerExceptionResolver.resolveException(request, response, null, exception);
            }
          filterChain.doFilter(request, response);
        }
    private String getCookieValue(HttpServletRequest req, String cookieName) {
        String token =  Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        return String.format("Bearer %s", token);
    }
    }

