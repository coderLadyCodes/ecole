package com.samia.ecole.security;

import com.samia.ecole.entities.Jwt;
import com.samia.ecole.entities.RefreshToken;
import com.samia.ecole.entities.User;
import com.samia.ecole.repositories.JwtRepository;
import com.samia.ecole.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@Configuration
@PropertySource("application.properties")
public class JwtService {
    public static final String BEARER = "bearer";
    public static final String REFRESH = "refresh";
    @Value("${encription.key}")
    private String ENCRIPTION_KEY;
    private final UserService userService;
    private final JwtRepository jwtRepository;

    public JwtService(UserService userService, JwtRepository jwtRepository) {
        this.userService = userService;
        this.jwtRepository = jwtRepository;
    }
    public Jwt tokenByValue(String value) {
        return this.jwtRepository.findByValeurAndDesactiveAndExpire(value, false, false).orElseThrow(()-> new RuntimeException("Token inconnu"));
    }
    public Map<String, String> generate(String username){
        User user = this.userService.loadUserByUsername(username);
        this.disableTokens(user);
        final Map<String, String> jwtMap = new java.util.HashMap<>(this.generateJwt(user));
        RefreshToken refreshToken = RefreshToken
                .builder()
                .valeur(UUID.randomUUID().toString())
                .expire(false)
                .creation(Instant.now())
                .expiration(Instant.now().plusMillis(7 * 24 * 60 * 60 * 1000))
                .build();
        final Jwt jwt = Jwt
                .builder()
                .valeur(jwtMap
                .get(BEARER))
                .desactive(false)
                .expire(false)
                .user(user)
                .refreshToken(refreshToken)
                .build();
        this.jwtRepository.save(jwt);
        jwtMap.put(REFRESH, refreshToken.getValeur());
        return jwtMap;
    }

    private void disableTokens(User user) {
        final List<Jwt> jwtList = this.jwtRepository.findByEmail(user.getEmail()).peek(
                jwt -> {
                    jwt.setDesactive(true);
                    jwt.setExpire(true);
                }
        ).collect(Collectors.toList());
        this.jwtRepository.saveAll(jwtList);
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }
    private Date getExpirationDateFromToken(String token){
        return this.getClaim(token, Claims::getExpiration);
    }
    private <T> T getClaim(String token, Function<Claims,T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
//                .setSigningKey(this.getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
                .verifyWith(this.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Map<String, String> generateJwt(User user) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 10 * 60 * 1000;

        final Map<String, Object> claims =  Map.of(
                "name", user.getName(),
                //"classroomId", user.getClassroomId(), // I INCLUDED CLASSROOMID IN THE CLAIMS
                //"role", user.getRole().name(),
                Claims.EXPIRATION,new Date(expirationTime),
                Claims.SUBJECT,user.getEmail()
        );

        final String bearer = Jwts.builder()
                // THIS DEPRECATED, CODE COMMENTED IS CORRECT (ALSO CHANGE GETALLCLAIMS ABOVE)
//                .setIssuedAt(new Date(currentTime))
//                .setExpiration(new Date(expirationTime))
//                .setSubject(user.getEmail())
//                .setClaims(claims)
//                .signWith(getKey(), SignatureAlgorithm.HS512)
//                .compact();
                  .issuer("self")
                  .issuedAt(new Date(currentTime))
                  .expiration(new Date(expirationTime))
                  .subject(user.getEmail())
                  .claims(claims)
                  .signWith(getKey())
                  .compact();
        return Map.of(BEARER, bearer);
    }

    private SecretKey getKey() { //changed the Key return to SecretKey
        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }
    public void deconnexion() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Jwt jwt = this.jwtRepository.findUserValidToken(user.getEmail(),false, false).orElseThrow(()-> new RuntimeException("Token invalide"));
    jwt.setExpire(true);
    jwt.setDesactive(true);
    this.jwtRepository.save(jwt);
    }
    //@Scheduled(cron = "@daily")
    @Scheduled(cron = "0 */1 * * * *")
    public void removeUselessJwt(){
        this.jwtRepository.deleteAllByExpireAndDesactive(true, true);
    }

    public Map<String, String> refreshToken(Map<String, String> refreshTokenRequest) {
        final Jwt jwt = this.jwtRepository.findByRefreshToken(refreshTokenRequest.get(REFRESH)).orElseThrow(()-> new RuntimeException("Token invalide"));
        if(jwt.getRefreshToken().isExpire() || jwt.getRefreshToken().getExpiration().isBefore(Instant.now())){
            throw new RuntimeException(("token invalide"));
        }
        this.disableTokens(jwt.getUser());
        return this.generate(jwt.getUser().getEmail());
    }
}
