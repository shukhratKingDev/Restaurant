package uz.uzkassa.smartposrestaurant.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.constants.AuthConstants;
import uz.uzkassa.smartposrestaurant.enums.LanguageEnum;
import uz.uzkassa.smartposrestaurant.enums.Role;
import uz.uzkassa.smartposrestaurant.security.UserAuth;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Stream;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 15:46
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        } else if (authentication.getPrincipal() instanceof UserAuth) {
            UserAuth userAuth = (UserAuth) authentication.getPrincipal();
            return userAuth.getUsername();
        }
        return null;
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional
            .ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && getAuthorities(authentication).noneMatch(Role.ANONYMOUS.getCode()::equals);
    }

    /**
     * Checks if the current user has any of the authorities.
     *
     * @param authorities the authorities to check.
     * @return true if the current user has any of the authorities, false otherwise.
     */
    public static boolean hasCurrentUserAnyOfAuthorities(String... authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (
            authentication != null && getAuthorities(authentication).anyMatch(authority -> Arrays.asList(authorities).contains(authority))
        );
    }

    /**
     * Checks if the current user has none of the authorities.
     *
     * @param authorities the authorities to check.
     * @return true if the current user has none of the authorities, false otherwise.
     */
    public static boolean hasCurrentUserNoneOfAuthorities(String... authorities) {
        return !hasCurrentUserAnyOfAuthorities(authorities);
    }

    /**
     * Checks if the current user has a specific authority.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean hasCurrentUserThisAuthority(String authority) {
        return hasCurrentUserAnyOfAuthorities(authority);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }

    public static String getLocaleCode() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional
            .ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserAuth) {
                    UserAuth userAuth = (UserAuth) authentication.getPrincipal();
                    return Optional.ofNullable(userAuth.getLanguage()).orElse(LanguageEnum.getDefaultLanguage().name());
                }
                return LanguageEnum.getDefaultLanguage().name();
            })
            .orElse(LanguageEnum.getDefaultLanguage().name());
    }

    public static Locale getLocale() {
        return Locale.forLanguageTag(getCurrentRequestLanguageEnum().name());
    }

    public static LanguageEnum getCurrentRequestLanguageEnum() {
        if (getCurrentRequest() != null && StringUtils.isNotEmpty(getCurrentRequest().getHeader(AuthConstants.LANGUAGE))) {
            return LanguageEnum.valueOf(getCurrentRequest().getHeader(AuthConstants.LANGUAGE));

        }
        return LanguageEnum.valueOf(getLocaleCode());
    }

    private static HttpServletRequest getCurrentRequest() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public static String getCurrentCompanyId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(
                securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserAuth) {
                    UserAuth userAuth = (UserAuth) authentication.getPrincipal();
                    return userAuth.getCompanyId();
                }
                return null;
            })
            .orElse(null);
    }

    public static String getCurrentBranchId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(
                securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserAuth) {
                    UserAuth userAuth = (UserAuth) authentication.getPrincipal();
                    return userAuth.getBranchId();
                }
                return null;
            }).orElse(null);

    }

    public static boolean isFromAdmin() {
        if (SecurityUtils.getCurrentRequest() != null) {
            return SecurityUtils.getCurrentRequest().getRequestURI().contains(ApiConstants.admin);
        }
        return false;
    }

    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(6, 6);
    }

    public static String generateSecretKey() {
        return UUID.randomUUID().toString();
    }

    public static String getCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional
            .ofNullable(securityContext.getAuthentication())
            .map(
                authentication -> {
                    if (authentication.getPrincipal() instanceof UserAuth) {
                        UserAuth userAuth = (UserAuth) authentication.getPrincipal();
                        return userAuth.getUserId();
                    }
                    return null;
                }
            )
            .orElse(null);
    }
}
