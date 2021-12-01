package pay.here.payheretest.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import pay.here.payheretest.entity.RefreshJwt;
import pay.here.payheretest.entity.User;
import pay.here.payheretest.repository.RefreshJwtRepository;
import pay.here.payheretest.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class JwtUtil {
	
    private String secretKey;
    private final UserRepository userRepository;
    private final RefreshJwtRepository refreshJwtRepository;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString("myKey".getBytes());
    }
    
    public String generateJwt(String email) {
        long jwtPeriod = 1000L * 60L * 10L; // 10분
//      long jwtPeriod = 1000L * 30L;
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        
        Date now = new Date();

        return Jwts.builder()
        		   .setClaims(claims)
                   .setIssuedAt(now)
                   .setExpiration(new Date(now.getTime() + jwtPeriod))
                   .signWith(SignatureAlgorithm.HS256, secretKey)
                   .compact();
    }
    
    public void saveRefreshJwt(String email) {
    	long refreshJwtPeriod = 1000L * 60L * 60L * 24L * 21L; // 3주
//    	long refreshJwtPeriod = 1000L * 60L;
    	
    	Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        
        Date now = new Date();

        String refreshJwt = Jwts.builder()
        		                .setClaims(claims)
                                .setIssuedAt(now)
                                .setExpiration(new Date(now.getTime() + refreshJwtPeriod))
                                .signWith(SignatureAlgorithm.HS256, secretKey)
                                .compact();
        
        User user = userRepository.findByEmail(email);
        
        ifExistDeleteJwt(user);
        
        refreshJwtRepository.save(RefreshJwt.builder()
        		                            .refreshJwt(refreshJwt)
        		                            .user(user)
        		                            .build());
    }
    
    private void ifExistDeleteJwt(User user) {
        Optional<RefreshJwt> oldRefreshJwt = refreshJwtRepository.findByUser(user);
          
        if (oldRefreshJwt.isPresent()) {
            refreshJwtRepository.delete(oldRefreshJwt.get());
        }
    }


    public boolean isExpired(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                                     .setSigningKey(secretKey)
                                     .parseClaimsJws(token);

            return claims.getBody()
                         .getExpiration()
                         .before(new Date());
            
        } catch (Exception e) {
            return true;
        }
    }
    
    public String getEmail(String token) {
    	return Jwts.parser()
                   .setSigningKey(secretKey)
                   .parseClaimsJws(token).getBody().get("email").toString();
    }
}