package uz.uzkassa.smartposrestaurant.web.rest.pub;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.auth.LoginDTO;
import uz.uzkassa.smartposrestaurant.dto.auth.TokenDTO;
import uz.uzkassa.smartposrestaurant.security.jwt.TokenProvider;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import javax.validation.Valid;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.05.2022 14:32
 */
@RestController
@RequestMapping(ApiConstants.publicAccountRootApi)
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PublicAccountResource {

    AuthenticationManagerBuilder authenticationManagerBuilder;
    TokenProvider tokenProvider;

    @PostMapping(ApiConstants.login)
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {

        log.debug("REST request to web login : {}", loginDTO.getPhone());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getPhone(), loginDTO.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication, loginDTO.isRememberMe());
        HttpHeaders headers = SecurityUtils.getHeader();
        headers.setBearerAuth(token);
        return new ResponseEntity<>(new TokenDTO(token), headers, HttpStatus.OK);
    }
}
