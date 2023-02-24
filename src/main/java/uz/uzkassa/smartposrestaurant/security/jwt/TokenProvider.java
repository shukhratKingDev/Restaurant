package uz.uzkassa.smartposrestaurant.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import tech.jhipster.config.JHipsterProperties;
import uz.uzkassa.smartposrestaurant.constants.AuthConstants;
import uz.uzkassa.smartposrestaurant.security.UserAuth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements AuthConstants {

    private final Key key;

    private final JwtParser jwtParser;

    private final Long tokenValidityInMilliseconds;

    private final Long tokenValidityInMillisecondsForRememberMe;

    public TokenProvider(JHipsterProperties jHipsterProperties) {
        byte[] keyBytes;
        String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret();
        if (!ObjectUtils.isEmpty(secret)) {
            keyBytes = Decoders.BASE64.decode(secret);
        } else {
            secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliseconds = 1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        String userId = null;
        String companyId = null;
        String branchId = null;
        String language = null;
        String tin = null;

        if (authentication.getPrincipal() instanceof UserAuth) {
            UserAuth customUser = (UserAuth) authentication.getPrincipal();
            userId = customUser.getUserId();
            branchId = customUser.getBranchId();
            companyId = customUser.getCompanyId();
            language = customUser.getLanguage();
            tin = customUser.getTin();
        }

        return Jwts
            .builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .claim(USER_ID, userId)
            .claim(BRANCH_ID, branchId)
            .claim(COMPANY_ID, companyId)
            .claim(LANGUAGE, language)
            .claim(TIN, tin)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(getNextExpiration(rememberMe))
            .compact();
    }

    private Date getNextExpiration(boolean rememberMe) {
        long now = (new Date()).getTime();
        Date expirationDate;
        if (rememberMe) {
            expirationDate = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            expirationDate = new Date(now + this.tokenValidityInMilliseconds);
        }
        return expirationDate;
    }

    public String createSwaggerToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return Jwts
            .builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .claim(USER_ID, authentication)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(getNextExpiration(rememberMe))
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
            .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .filter(auth -> !auth.trim().isEmpty())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        String userId = claims.get(USER_ID, String.class);
        String companyId = claims.get(COMPANY_ID, String.class);
        String branchId = claims.get(BRANCH_ID, String.class);
        String language = claims.get(LANGUAGE, String.class);
        String tin = claims.get(TIN, String.class);

        UserAuth principal = new UserAuth(claims.getSubject(), "", authorities, userId, companyId, branchId, language, tin);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token trace.", e);
        }
        return false;
    }
}
