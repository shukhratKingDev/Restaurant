package uz.uzkassa.smartposrestaurant.web.rest;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.dto.SwaggerUserDTO;
import uz.uzkassa.smartposrestaurant.dto.auth.AdminLoginDTO;
import uz.uzkassa.smartposrestaurant.dto.auth.JWTToken;
import uz.uzkassa.smartposrestaurant.security.jwt.TokenProvider;
import uz.uzkassa.smartposrestaurant.service.UserService;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;
import uz.uzkassa.smartposrestaurant.web.rest.errors.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 25.10.2022 20:24
 */
@RestController
@RequestMapping(ApiConstants.swaggerRootApi)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class SwaggerResource {

    UserService userService;
    TokenProvider tokenProvider;
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping(ApiConstants.authenticateApi)
    public String isAuthenticated(HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @PostMapping(ApiConstants.authenticateApi)
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody AdminLoginDTO adminLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            adminLoginDTO.getUsername(),
            adminLoginDTO.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication, adminLoginDTO.isRememberMe());
        HttpHeaders httpHeaders = SecurityUtils.getHeader();
        httpHeaders.setBearerAuth(token);
        return new ResponseEntity<>(new JWTToken(token), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/account")
    public SwaggerUserDTO getAccount() {
        return userService
            .getUserWithAuthorities()
            .map(SwaggerUserDTO::new)
            .orElseThrow(() -> new NotFoundException(User.class.getSimpleName()));
    }

    @GetMapping("/authorities")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }
}
