package uz.uzkassa.smartposrestaurant.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.Optional;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtils.getCurrentUserId());
    }
}
