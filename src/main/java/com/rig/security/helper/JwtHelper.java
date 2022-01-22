package com.rig.security.helper;

import com.rig.constant.Constant;
import com.rig.model.entity.User;
import com.rig.security.JwtProperties;
import com.rig.security.JwtTokenTypeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    private final JwtProperties jwtProperties;

    public JwtHelper(final JwtProperties jwtProperties) { this.jwtProperties = jwtProperties; }

    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(final String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date()); //Expiration date can be placed here;
    }

    public String generateToken(final User user, final JwtTokenTypeEnum jwtTokenType) {
        final Map<String, Object> claims = new HashMap<>();

        claims.put(Constant.JWT_TOKEN_TYPE, jwtTokenType.getType());

        return createToken(claims, user.getEmail(), getExpirationTime());
    }

    public String createToken(final Map<String, Object> claims, final String subject, final Integer expiration) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public String getSecretKey() { return jwtProperties.getSecretKey(); }

    public int getExpirationTime() { return jwtProperties.getTokenExpirationMilliseconds(); }

    public String extractEmail(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String getTokenHeader() { return jwtProperties.getTokenHeader(); }
}
