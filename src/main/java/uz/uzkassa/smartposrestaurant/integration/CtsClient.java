package uz.uzkassa.smartposrestaurant.integration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uz.uzkassa.smartposrestaurant.config.ApplicationProperties;
import uz.uzkassa.smartposrestaurant.dto.auth.AdminLoginDTO;
import uz.uzkassa.smartposrestaurant.dto.auth.TokenDTO;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;
import uz.uzkassa.smartposrestaurant.web.rest.errors.IntegrationException;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 16:37
 */
@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class CtsClient {
    ApplicationProperties applicationProperties;
    RestTemplate restTemplate;
    static LocalDateTime authenticateRefreshTime;
    final long tokenValidityInSecondsForRememberMe = 2592000;
    static String tokenStore;

    public String getToken() {
        if (tokenStore != null && authenticateRefreshTime != null && authenticateRefreshTime.isAfter(LocalDateTime.now())) {
            return tokenStore;
        }
        try {
            AdminLoginDTO dto = new AdminLoginDTO();
            dto.setPassword(applicationProperties.getCtsConfig().getPassword());
            dto.setUsername(applicationProperties.getCtsConfig().getUsername());
            dto.setRememberMe(true);

            HttpHeaders headers = SecurityUtils.getHeader();
            HttpEntity<AdminLoginDTO> entity = new HttpEntity<>(dto, headers);
            ResponseEntity<TokenDTO> response = restTemplate.exchange(
                applicationProperties.getCtsConfig().getAuthUrl(),
                HttpMethod.POST,
                entity,
                TokenDTO.class
            );
            tokenStore = Objects.requireNonNull(response.getBody()).getAccessToken();
            authenticateRefreshTime = LocalDateTime.now().plusSeconds(tokenValidityInSecondsForRememberMe);
            return tokenStore;
        } catch (Exception e) {
            tokenStore = null;
            if (e instanceof IntegrationException) {
                throw new IntegrationException(e.getMessage(), ((IntegrationException) e).getError());
            }
            throw new IntegrationException(e.getMessage());
        }
    }
}
