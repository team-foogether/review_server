package foogether.review.utils.auth;

import foogether.review.client.UserClient;
import foogether.review.repository.ReviewRepository;
import foogether.review.service.JwtService;
import foogether.review.web.dto.DefaultResponse;
import foogether.review.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor

public class AuthAspect {
    private final static String AUTHORIZATION = "Authorization";

    private final static DefaultResponse DEFAULT_RES = DefaultResponse.builder().message("인증 실패").build();
    private final static ResponseEntity<DefaultResponse> RES_RESPONSE_ENTITY = new ResponseEntity<>(DEFAULT_RES, HttpStatus.UNAUTHORIZED);

    private final HttpServletRequest httpServletRequest;

    @Autowired
    private final ReviewRepository meetingRepository;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    UserClient userClient;

    /**
     * 토큰 유효성 검사
     * @param pjp
     * @return
     * @throws Throwable
     */

    @Around("@annotation(foogether.review.utils.auth.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String jwt = httpServletRequest.getHeader(AUTHORIZATION);

        if (jwt == null) return RES_RESPONSE_ENTITY;

        final JwtService.Token token = jwtService.decode(jwt);

        if (token == null) {
            return RES_RESPONSE_ENTITY;
        } else {
            UserResponseDto userResponseDto;
            try {
                final ResponseEntity<DefaultResponse<UserResponseDto>> userInfo
                        = userClient.getUserInfo(token.getUserIdx());
                userResponseDto = new UserResponseDto(userInfo.getBody().getData());
            } catch (Exception e){
                return RES_RESPONSE_ENTITY;
            }

            if (userResponseDto == null) return RES_RESPONSE_ENTITY;
            return pjp.proceed(pjp.getArgs());
        }
    }
}
