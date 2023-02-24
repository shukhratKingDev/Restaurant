package uz.uzkassa.smartposrestaurant.web.rest.cabinet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.auth.*;
import uz.uzkassa.smartposrestaurant.dto.user.ProfileDTO;
import uz.uzkassa.smartposrestaurant.dto.user.ProfileDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.user.UserExistDTO;
import uz.uzkassa.smartposrestaurant.dto.user.UserIdDTO;
import uz.uzkassa.smartposrestaurant.security.jwt.TokenProvider;
import uz.uzkassa.smartposrestaurant.service.UserService;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 14:01
 */
@RestController
@RequestMapping(ApiConstants.cabinetAccountRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Slf4j
public class CabinetAccountResource {

    UserService userService;
    TokenProvider tokenProvider;
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping(ApiConstants.check)
    public ResponseEntity<UserExistDTO> check(@Valid @RequestBody PhoneDTO phoneDTO) {
        return ResponseEntity.ok().body(userService.check(phoneDTO));
    }

    @PostMapping(ApiConstants.verify)
    public ResponseEntity<SecretKeyDTO> verify(@Valid @RequestBody VerifyDTO verifyDTO) {
        return ResponseEntity.ok().body(userService.verify(verifyDTO));
    }

    @PostMapping(ApiConstants.register)
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(ApiConstants.resendActivationKey)
    public ResponseEntity<Void> resendActivationKey(@Valid @RequestBody PhoneDTO phoneDTO) {
        userService.resendActivationKey(phoneDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(ApiConstants.resetPasswordInit)
    public ResponseEntity<Void> resetPasswordInit(@Valid @RequestBody PhoneDTO phoneDTO) {
        userService.resetPasswordInit(phoneDTO.getPhone());
        return ResponseEntity.ok().build();
    }

    @PostMapping(ApiConstants.resendResetPasswordKey)
    public ResponseEntity<Void> resendPasswordResetKey(@Valid @RequestBody PhoneDTO phoneDTO) {
        userService.resetPasswordInit(phoneDTO.getPhone());
        return ResponseEntity.ok().build();
    }

    @PostMapping(ApiConstants.resetPasswordCheck)
    public ResponseEntity<SecretKeyDTO> resetPasswordCheck(@Valid @RequestBody VerifyDTO verifyDTO) {
        return ResponseEntity.ok().body(userService.resetPasswordCheck(verifyDTO));
    }

    @PostMapping(ApiConstants.resetPasswordFinish)
    public ResponseEntity<Void> resetPasswordFinish(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.resetPasswordFinish(registerDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping(ApiConstants.changePasswordInit)
    public ResponseEntity<SecretKeyDTO> changePasswordInit() {
        return ResponseEntity.ok().body(userService.changePasswordInit());
    }

    @PostMapping(ApiConstants.changePasswordConfirm)
    public ResponseEntity<Void> changePasswordConfirm(@Valid @RequestBody PasswordDTO passwordDTO) {
        userService.changePasswordConfirm(passwordDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping(ApiConstants.changeLogin)
    public ResponseEntity<SecretKeyDTO> changeLogin(@Valid @RequestBody LoginDTO loginDTO) { // todo
        return ResponseEntity.ok().body(userService.changeLogin(loginDTO.getPhone()));
    }

    @PutMapping(ApiConstants.changeLoginConfirm)
    public ResponseEntity<Void> changeLoginConfirm(@Valid @RequestBody UpdateLoginDTO updateLoginDTO) {
        userService.changeLoginConfirm(
            updateLoginDTO.getPhone(),
            updateLoginDTO.getActivationKey(),
            updateLoginDTO.getSecretKey(),
            null,
            null
        );
        return ResponseEntity.ok().build();
    }


    @PostMapping(path = "/upload/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadProfileImage(@RequestPart("file") MultipartFile file) {
        userService.uploadProfileImage(file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/profile-image")
    public ResponseEntity<Void> deleteProfileImage() {
        userService.deleteProfileImage();
        return ResponseEntity.ok().build();
    }

    @PostMapping(ApiConstants.profile)
    public ResponseEntity<ProfileDTO> saveProfile(@RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok().body(userService.saveProfile(profileDTO));// todo
    }

    @GetMapping(ApiConstants.profile)
    public ResponseEntity<ProfileDetailDTO> getProfile() {
        return ResponseEntity.ok().body(userService.getProfile());
    }

    @GetMapping("/client/{phone}")
    public ResponseEntity<UserIdDTO> getActiveClient(@PathVariable(value = "phone") String phone) {
        return ResponseEntity.ok().body(userService.getActiveClient(phone));
    }

    @PutMapping(ApiConstants.profile)
    public ResponseEntity<ProfileDetailDTO> updateProfile(@RequestBody ProfileDetailDTO profileDetailDTO) {
        return ResponseEntity.ok().body(userService.updateProfile(profileDetailDTO));
    }

    @PutMapping("/language/{code}")
    public ResponseEntity<Void> updateLanguage(@PathVariable String code) {//todo
        userService.updateLanguage(code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/complete-registration")
    public ResponseEntity<Void> completeRegistration(@Valid @RequestBody PhoneDTO phoneDTO) {
        userService.completeRegistration(phoneDTO.getPhone());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = ApiConstants.logout, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return ResponseEntity.ok().build();
    }

}
