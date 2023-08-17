package com.example.bff.rest.securityConfig;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.bff.core.services.authentication.ApplicationUserDetailsService;
import com.example.bff.core.services.authentication.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final List<String> whitelisted = List.of("/coupon","/coupon/**");
    private final ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //if(this.isPermittedPath(request.getRequestURI())){
        //    filterChain.doFilter(request,response);
        //    return;
        //}

        Optional<String> header = Optional.ofNullable(request.getHeader("Authorization"));
        if (header.isEmpty()) {
            filterChain.doFilter(request, response);
        }
        if (header.isPresent()) {
            String token = header.get().substring(7);

            try {
                String email = jwtService.getEmail(token);
                UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(email);
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
                filterChain.doFilter(request, response);
            } catch (JWTVerificationException e) {
                response.setContentType("application/json");
                response.setStatus(403);
                response.setCharacterEncoding("UTF-8");

                PrintWriter printWriter = response.getWriter();
                printWriter.println("Invalid token");
                printWriter.flush();
            }
        }
    }
    private Boolean isPermittedPath(String path) {

        return this.whitelisted.stream()
                .anyMatch((e -> context.getBean(AntPathMatcher.class).match(e, path)));

    }
}
