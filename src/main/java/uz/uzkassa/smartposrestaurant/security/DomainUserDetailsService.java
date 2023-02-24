package uz.uzkassa.smartposrestaurant.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.enums.LanguageEnum;
import uz.uzkassa.smartposrestaurant.enums.UserStatus;
import uz.uzkassa.smartposrestaurant.repository.UserRepository;
import uz.uzkassa.smartposrestaurant.service.LocalizationService;
import uz.uzkassa.smartposrestaurant.web.rest.errors.BadRequestException;
import uz.uzkassa.smartposrestaurant.web.rest.errors.NotFoundException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 07.10.2022  19:56
 */

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LocalizationService localizationService;

    public DomainUserDetailsService(UserRepository userRepository, LocalizationService localizationService) {
        this.userRepository = userRepository;
        this.localizationService = localizationService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository
            .findOneWithAuthoritiesByLoginAndDeletedFalse(lowercaseLogin)
            .map(this::createSpringFrameworkUser)
            .orElseThrow(() ->
                new NotFoundException(
                    localizationService.localize("entity.not.found.with",
                        new Object[]{localizationService.localize(uz.uzkassa.smartposrestaurant.domain.auth.User.class.getSimpleName(), lowercaseLogin)}
                    )
                )
            );
    }

    private User createSpringFrameworkUser(uz.uzkassa.smartposrestaurant.domain.auth.User user) {
        List<GrantedAuthority> grantedAuthorities = user
            .getAuthorities()
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        if (UserStatus.PENDING.equals(user.getStatus())) {
            throw new BadRequestException(localizationService.localize("user.not.activated", new Object[]{user.getLogin()}));
        }
        if (UserStatus.BLOCKED.equals(user.getStatus())) {
            throw new BadRequestException(localizationService.localize("user.blocked", new Object[]{user.getLogin()}));
        }
        LanguageEnum language = Optional.ofNullable(user.getLanguage()).orElse(LanguageEnum.getDefaultLanguage());
        return new UserAuth(
            user.getLogin(),
            user.getPassword(),
            grantedAuthorities,
            user.getId(),
            user.getCompanyId(),
            user.getBranchId(),
            language.name(),
            user.getCompany() != null ? user.getCompany().getTin() : null
        );
    }
}
