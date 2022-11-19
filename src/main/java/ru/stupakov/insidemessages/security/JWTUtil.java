package ru.stupakov.insidemessages.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author Stupakov D. L.
 **/
@Component
public class JWTUtil {
    @Value("{jwt_secret}")
    private String jwtSecret;

    /*
        Генерируем токен.
        Записываем в токен только name.
     */
    public String generateToken(String name){
        //Срок действия токена
        Date expirationDate = Date.from(ZonedDateTime.now().plusHours(2).toInstant());

        return JWT.create()
                .withIssuedAt(new Date())
                .withClaim("name", name)
                .withIssuer("messages")
                .withExpiresAt(expirationDate)
                .withSubject("User details")
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    /*
      Валидируем токен
     */
    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withSubject("User details")
                .withIssuer("messages")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("name").asString();
    }



}
