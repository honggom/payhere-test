package pay.here.payheretest.jwt;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import pay.here.payheretest.entity.RefreshJwt;
import pay.here.payheretest.repository.RefreshJwtRepository;
import pay.here.payheretest.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
	
	private static final int UNAUTHORIZED = 401;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final RefreshJwtRepository refreshJwtRepository;
    
	/*
	 * 요구사항 4.
	 * 로그인하지 않은 고객은 가계부 내역에 대한 접근 제한 처리가 되어야 합니다. 
	 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Request URI ===> " + "'" + request.getRequestURI() + "'");
        response.setContentType("text/plain;charset=UTF-8");
        
        if(request.getMethod().equals("OPTIONS")) {
			return true;
		} else {
			String jwt = request.getHeader("jwt-auth-token");
			String email = request.getHeader("email");
			Optional<RefreshJwt> refreshJwtOptional = refreshJwtRepository.findByUser(userRepository.findByEmail(email));
			
			if (refreshJwtOptional.isPresent()) {
				String refreshJwt = refreshJwtOptional.get().getRefreshJwt();
				
				if (jwt != null && jwt.length() > 0) {
					logger.info("JWT INFO : JWT, REFRESH 토큰이 존재함");
					
					if (jwtUtil.isExpired(jwt)) {
						if (jwtUtil.isExpired(refreshJwt)) {
							logger.info("JWT INFO : JWT, REFRESH 토큰이 만료됨 (로그아웃 상태)");
							refreshJwtRepository.delete(refreshJwtOptional.get());
							
			                response.setStatus(UNAUTHORIZED);
			                return false;
						} else {
							logger.info("JWT INFO : JWT 토큰이 만료 됐지만 REFRESH 토큰이 유효함 ===> 토큰 갱신");
							
							response.setHeader("jwt-auth-token", jwtUtil.generateJwt(email));
							response.setHeader("refresh-email", email);
							
							jwtUtil.saveRefreshJwt(email);
							return true;
						}
					} else {
						logger.info("JWT INFO : REFRESH 토큰이 존재하며 JWT 토큰이 유효함");
						return true;
					}
				} else {
					logger.info("JWT INFO : REFRESH 토큰이 존재하나 JWT 토큰이 존재하지 않음");
					refreshJwtRepository.delete(refreshJwtOptional.get());
					
					response.setStatus(UNAUTHORIZED);
					return false;
				}
			} else {
                logger.info("JWT INFO : REFRESH 토큰이 존재하지 않음 (로그아웃 상태)");
                
                response.setStatus(UNAUTHORIZED);
                return false;
			}
		}
    }
    
}