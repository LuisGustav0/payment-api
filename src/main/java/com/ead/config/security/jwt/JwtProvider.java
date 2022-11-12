package com.ead.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Log4j2
@Component
public class JwtProvider {

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;

    private String getSubjectJwt(final String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public UUID getUserIdByJwt(final String token) {
        final String subject = getSubjectJwt(token);

        return UUID.fromString(subject);
    }

    private Object getClaimNameJwt(final String token, final String claimName) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get(claimName);
    }

    public String getListAuthority(final String token) {
        return (String) getClaimNameJwt(token, "authorities");
    }

    public boolean notValidateJwt(final String token) {
        return !this.validateJwt(token);
    }

    private boolean validateJwt(final String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature: {}", ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token: {}", ex);
        } catch (ExpiredJwtException ex) {
            log.error("JWT token is expired: {}", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("JWT token is unsupported: {}", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty: {}", ex);
        }
        return false;
    }
}
