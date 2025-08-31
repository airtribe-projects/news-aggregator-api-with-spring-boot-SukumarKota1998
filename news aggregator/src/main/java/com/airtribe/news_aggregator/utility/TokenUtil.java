package com.airtribe.news_aggregator.utility;

import com.airtribe.news_aggregator.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class TokenUtil {

    private static final String jwtSecretKey = "sukumarAirtibec13javaspringbootnewaggrigatorprojectsecretkeyexample";

    public static String generateToken(User user) {
        return Jwts.builder().subject(user.getUserName()).setIssuedAt(new Date()).setExpiration(
                        new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000))
                .claim("roles", "ROLE_" + user.getRole())
                .claim("emailId", user.getEmailId())

                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();

    }

    public static Claims validateSignedToken(String authorizationHeader) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(authorizationHeader)
                    .getBody();
            System.out.print("Claims: " + body);
            return body;
        } catch (io.jsonwebtoken.ExpiredJwtException exception) {
            System.err.println("JWT token is expired: " + exception.getMessage());
            return null;
        } catch (io.jsonwebtoken.MalformedJwtException exception) {
            System.err.println("Invalid JWT token: " + exception.getMessage());
            return null;
        } catch (io.jsonwebtoken.UnsupportedJwtException exception) {
            System.err.println("JWT token is unsupported: " + exception.getMessage());
            return null;
        } catch (IllegalArgumentException exception) {
            System.err.println("JWT claims string is empty: " + exception.getMessage());
            return null;
        }
    }

    public static String getEmailIdFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
