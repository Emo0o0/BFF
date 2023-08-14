package com.example.bff.core.services.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bff.api.inputoutput.user.loginUser.LoginUserInput;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Component
public class JwtService {

    private final Duration TOKEN_VALIDITY = Duration.of(30, ChronoUnit.DAYS);
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final String jwtSecret = "JKLDRIO456098DFGDF905OIR6YJK";

    public String generateJwt(LoginUserInput input) {
        UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(input.getEmail());
        return JWT.create()
                .withClaim("email", userDetails.getUsername())
                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(TOKEN_VALIDITY))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String getEmail(String jwt) {
        DecodedJWT decoded = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withClaimPresence("email")
                .build()
                .verify(jwt);

        return decoded.getClaim("email").asString();
    }

}
