package foogether.review.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

import static com.auth0.jwt.JWT.require;

@Slf4j
@Service
public class JwtService {

    @Value("${JWT.ISSUER}")
    private String ISSUER;

    @Value("${JWT.SECRET}")
    private String SECRET;

    // 토큰 생성
    public String create(final int userIdx) {
        try {
            JWTCreator.Builder b = JWT.create();
            b.withIssuer(ISSUER);
            b.withClaim("userIdx", userIdx);
            b.withExpiresAt(expiresAt());

            return b.sign(Algorithm.HMAC256(SECRET));
        } catch (JWTCreationException JwtCreationException) {
            log.info(JwtCreationException.getMessage());
        }
        return null;
    }

    // exprires
    private Date expiresAt() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 744);
        return cal.getTime();
    }


    public Token decode(final String token) {
        try {

            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return new Token(decodedJWT.getClaim("userIdx").asLong().intValue());
        } catch (Exception jve) {
            log.error(jve.getMessage());
        }
        return new Token();
    }

    public static class Token {
        private int userIdx = -1;

        public Token() {
        }

        public Token(final int userIdx) {
            this.userIdx = userIdx;
        }

        public int getUserIdx() {
            return userIdx;
        }
    }

    @Data
    public static class TokenRes {
        //실제 토큰
        private String token;
        //userIdx 반환
        private int userIdx;

        public TokenRes() {
        }

        public TokenRes(final String token, final int userIdx) {
            this.token = token;
            this.userIdx = userIdx;
        }
    }
    //권한
    public boolean checkAuth(final String header, final int userIdx) {
        return decode(header).getUserIdx() == userIdx;
    }
}
