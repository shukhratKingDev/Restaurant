package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.uzkassa.smartposrestaurant.domain.FileUpload;
import uz.uzkassa.smartposrestaurant.domain.SmsHistory;
import uz.uzkassa.smartposrestaurant.domain.UserAuth;
import uz.uzkassa.smartposrestaurant.domain.auth.Authority;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.company.Company;
import uz.uzkassa.smartposrestaurant.dto.auth.*;
import uz.uzkassa.smartposrestaurant.dto.reservation.ClientDTO;
import uz.uzkassa.smartposrestaurant.dto.sms.SmsRequestDTO;
import uz.uzkassa.smartposrestaurant.dto.user.ProfileDTO;
import uz.uzkassa.smartposrestaurant.dto.user.ProfileDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.user.UserExistDTO;
import uz.uzkassa.smartposrestaurant.dto.user.UserIdDTO;
import uz.uzkassa.smartposrestaurant.enums.*;
import uz.uzkassa.smartposrestaurant.repository.AuthorityRepository;
import uz.uzkassa.smartposrestaurant.repository.FileUploadRepository;
import uz.uzkassa.smartposrestaurant.repository.SmsHistoryRepository;
import uz.uzkassa.smartposrestaurant.repository.UserAuthRepository;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;
import uz.uzkassa.smartposrestaurant.web.rest.errors.BadRequestException;
import uz.uzkassa.smartposrestaurant.web.rest.errors.EntityAlreadyExistException;
import uz.uzkassa.smartposrestaurant.web.rest.errors.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 15:00
 */
@Service
@Slf4j
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserService extends BaseService {

    AuthorityRepository authorityRepository;
    SmsService smsService;
    FileUploadRepository fileUploadRepository;
    CacheService cacheService;
    PasswordEncoder passwordEncoder;
    FileUploadService fileUploadService;
    SmsHistoryRepository smsHistoryRepository;
    UserAuthRepository userAuthRepository;

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLoginAndDeletedFalse);
    }

    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserExistDTO check(PhoneDTO phoneDTO) {
        User user = findOneByLogin(phoneDTO.getPhone()).orElse(null);
        if (user == null) {
            smsService.sendQueue(new SmsRequestDTO(phoneDTO.getPhone(), SecurityUtils.generateActivationKey()));
            return new UserExistDTO(false, null);
        }
        if (UserStatus.PENDING.equals(user.getStatus())) {
            smsService.sendQueue(new SmsRequestDTO(phoneDTO.getPhone(), SecurityUtils.generateActivationKey()));
            return new UserExistDTO(true, UserStatus.PENDING);
        }
        return new UserExistDTO(true, user.getStatus());
    }

    @Transactional(readOnly = true)
    public Optional<User> findOneByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLoginAndDeletedFalse(login);
    }

    public SecretKeyDTO verify(VerifyDTO verifyDTO) {
        SmsHistory smsHistory = smsService.getByPhoneAndCode(verifyDTO.getPhone(), verifyDTO.getActivationKey()).orElse(null);
        if (smsHistory == null) {
            throw new BadRequestException(localizationService.localize("incorrect.activation.key"));
        }
        UserAuth userAuth = new UserAuth();
        userAuth.setPhone(verifyDTO.getPhone());
        userAuth.setSecretKey(UUID.randomUUID().toString());
        userAuth = userAuthRepository.save(userAuth);
        smsHistory.setDeleted(true);
        smsHistoryRepository.save(smsHistory);
        userAuthRepository.deleteUserSecretKeys(verifyDTO.getPhone(), userAuth.getId());
        return new SecretKeyDTO(userAuth.getSecretKey());
    }


    public void register(RegisterDTO registerDTO) {
        if (
            userAuthRepository.findFirstByPhoneAndSecretKeyAndDeletedFalseOrderByCreatedDateDesc(registerDTO.getPhone(), registerDTO.getSecretKey())
                .isEmpty()
        ) {
            throw new BadRequestException(localizationService.localize("incorrect.secret.key"));
        }
        if (!Objects.equals(registerDTO.getPassword(), registerDTO.getPasswordConfirmation())) {
            throw new BadRequestException(localizationService.localize("user.password.dont.match"));
        }
        if (!registerDTO.getPassword().matches(registerDTO.getPasswordConfirmation())) {
            throw new BadRequestException(localizationService.localize("user.password.dont.match.symbol"));
        }

        User user = userRepository
            .findOneWithAuthoritiesByLoginAndDeletedFalse(registerDTO.getPhone())
            .orElse(null);
        if (user == null) {
            Set<Authority> authorities = new HashSet<>();
            authorityRepository.findById(Role.BUSINESS_OWNER.getCode()).ifPresent(authorities::add);
            user = new User();
            user.setLogin(registerDTO.getPhone());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setAuthorities(authorities);
            user.setStatus(UserStatus.ACTIVE);
            user.setStage(Stage.REGISTERED);
            user.setLanguage(LanguageEnum.get(registerDTO.getLanguage().getCode()));
            userRepository.save(user);
            this.deleteUserCache(user);
        }
        userAuthRepository.deleteUserSecretKeys(registerDTO.getPhone());
    }

    public User createClient(ClientDTO clientDTO) {
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(Role.CLIENT.getCode()).ifPresent(authorities::add);
        User client = new User();
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setLogin(clientDTO.getPhone());
        client.setPhone(clientDTO.getPhone());
        client.setPassword(passwordEncoder.encode(SecurityUtils.generateActivationKey()));
        client.setAuthorities(authorities);
        userRepository.save(client);
        this.deleteUserCache(client);
        return client;
    }

    private void deleteUserCache(User user) {
        cacheService.deleteUserCache(user.getLogin());
    }

    public void resendActivationKey(PhoneDTO phoneDTO) {
        smsHistoryRepository.deleteAllByPhone(phoneDTO.getPhone());
        smsService.sendQueue(new SmsRequestDTO(phoneDTO.getPhone(), SecurityUtils.generateActivationKey()));
    }

    public void resetPasswordInit(String phone) {
        User user = findOneByLogin(phone).orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "phone", phone));
        String resetKey = SecurityUtils.generateActivationKey();
        smsService.sendQueue(new SmsRequestDTO(phone, resetKey));
        user.setResetKey(resetKey);
        user.setResetDate(LocalDateTime.now());
        userRepository.save(user);
        this.deleteUserCache(user);
    }

    public SecretKeyDTO resetPasswordCheck(VerifyDTO verifyDTO) {
        User user = userRepository
            .findOneWithAuthoritiesByLoginAndDeletedFalse(verifyDTO.getPhone())
            .orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "phone", verifyDTO.getPhone()));
        if (!verifyDTO.getActivationKey().matches(user.getResetKey())) {
            throw new BadRequestException(
                localizationService.localize("invalid.confirmation.key", new Object[]{verifyDTO.getActivationKey()})
            );
        }
        UserAuth userAuth = new UserAuth();
        userAuth.setPhone(verifyDTO.getPhone());
        userAuth.setSecretKey(SecurityUtils.generateSecretKey());
        userAuthRepository.save(userAuth);
        userAuthRepository.deleteUserSecretKeys(verifyDTO.getPhone(), userAuth.getId());
        return new SecretKeyDTO(userAuth.getSecretKey());
    }

    public void resetPasswordFinish(RegisterDTO registerDTO) {
        User user = userRepository
            .findOneWithAuthoritiesByLoginAndDeletedFalse(registerDTO.getPhone())
            .orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "phone", registerDTO.getPassword()));
        if (!Objects.equals(registerDTO.getPassword(), registerDTO.getPasswordConfirmation())) {
            throw new BadRequestException(localizationService.localize("user.password.dont.match"));
        }
        if (!registerDTO.getPassword().matches(registerDTO.getPasswordConfirmation())) {
            throw new BadRequestException(localizationService.localize("user.password.dont.match.symbol"));
        }
        if (
            userAuthRepository.findFirstByPhoneAndSecretKeyAndDeletedFalseOrderByCreatedDateDesc(registerDTO.getPhone(), registerDTO.getSecretKey())
                .isEmpty()
        ) {
            throw new BadRequestException(localizationService.localize("incorrect.secret.key"));
        }
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setResetKey(null);
        user.setResetDate(null);
        userRepository.save(user);
        this.deleteUserCache(user);
        userAuthRepository.deleteUserSecretKeys(registerDTO.getPhone());
    }

    public SecretKeyDTO changePasswordInit() {
        User user = getCurrentUser();
        String activationKey = SecurityUtils.generateActivationKey();
        smsService.sendQueue(new SmsRequestDTO(user.getLogin(), activationKey));
        user.setActivationKey(activationKey);
        userRepository.save(user);
        this.deleteUserCache(user);

        UserAuth userAuth = new UserAuth();
        userAuth.setPhone(user.getLogin());
        userAuth.setSecretKey(SecurityUtils.generateSecretKey());
        userAuthRepository.save(userAuth);
        userAuthRepository.deleteUserSecretKeys(user.getLogin(), userAuth.getId());
        return new SecretKeyDTO(userAuth.getSecretKey());
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(unauthorizedException());
        log.debug("Get current user " + userLogin);
        return userRepository
            .findOneWithAuthoritiesByLoginAndDeletedFalse(userLogin)
            .orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "login", userLogin));
    }

    private Supplier<UnauthorizedException> unauthorizedException() {
        return () -> new UnauthorizedException(localizationService.localize("session.expired"));
    }

    public void changePasswordConfirm(PasswordDTO passwordDTO) {
        User user = getCurrentUser();

        if (!Objects.equals(passwordDTO.getActivationKey(), user.getActivationKey())) {
            throw new BadRequestException(
                localizationService.localize("invalid.confirmation.key", new Object[]{user.getAuthorities()})
            );
        }
        if (!Objects.equals(passwordDTO.getPassword(), passwordDTO.getPasswordConfirmation())) {
            throw new BadRequestException(
                localizationService.localize("user.password.dont.match")
            );
        }
        if (!passwordDTO.getPassword().matches(passwordDTO.getPassword())) {
            throw new BadRequestException(
                localizationService.localize("user.password.dont.match.symbol")
            );
        }

        if (
            userAuthRepository
                .findFirstByPhoneAndSecretKeyAndDeletedFalseOrderByCreatedDateDesc(
                    user.getLogin(),
                    passwordDTO.getSecretKey()
                ).isEmpty()
        ) {
            throw new BadRequestException(localizationService.localize("incorrect.secret.key"));
        }
        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        user.setActivationKey(null);
        userRepository.save(user);
        this.deleteUserCache(user);
        userAuthRepository.deleteUserSecretKeys(user.getLogin());
    }

    public SecretKeyDTO changeLogin(String login) {
        if (findOneByLogin(login).isPresent()) {
            throw new EntityAlreadyExistException(localizationService.localize("user.already.exists.by.phone", new Object[]{login}));
        }
        User user = getCurrentUser();
        String activationKey = SecurityUtils.generateActivationKey();
        smsService.sendQueue(new SmsRequestDTO(user.getLogin(), activationKey));
        user.setActivationKey(activationKey);
        userRepository.save(user);
        this.deleteUserCache(user);

        UserAuth userAuth = new UserAuth();
        userAuth.setPhone(user.getLogin());
        userAuth.setSecretKey(UUID.randomUUID().toString());
        userAuthRepository.save(userAuth);
        userAuthRepository.deleteUserSecretKeys(user.getLogin(), userAuth.getId());
        return new SecretKeyDTO(userAuth.getSecretKey());

    }

    public void changeLoginConfirm(String login, String activationKey, String secretKey,
                                   HttpServletRequest request, HttpServletResponse response) {

        User oldUser = getCurrentUser();

        User user = getCurrentUser();

        if (!Objects.equals(user.getActivationKey(), activationKey)) {
            throw new BadRequestException(
                localizationService.localize("invalid.confirmation.key")
            );
        }
        if (userAuthRepository.findFirstByPhoneAndSecretKeyAndDeletedFalseOrderByCreatedDateDesc(user.getLogin(), secretKey).isEmpty()) {
            throw new BadRequestException(
                localizationService.localize("incorrect.secret.key")
            );
        }
        this.deleteUserCache(user);

        userAuthRepository.deleteUserSecretKeys(oldUser.getLogin());
        user.setActivationKey(null);
        user.setLogin(login);
        userRepository.save(user);
        this.deleteUserCache(user);
        userAuthRepository.deleteUserSecretKeys(login);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && request != null && response != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            SecurityContextHolder.getContext().setAuthentication(null);
        }

    }

    public void uploadProfileImage(MultipartFile image) {
        validateFile(image);
        User user = getCurrentUser();
        if (user.getProfilePhotoId() != null) {
            fileUploadRepository.deleteById(user.getProfilePhotoId());
        }
        FileUpload profileImage = fileUploadService.fileUpload(image);
        user.setProfilePhoto(profileImage);
        user.setProfilePhotoId(profileImage.getId());
        userRepository.save(user);
        this.deleteUserCache(user);
    }

    public void deleteProfileImage() {
        User user = getCurrentUser();
        if (user.getProfilePhotoId() != null) {
            fileUploadRepository.deleteById(user.getProfilePhotoId());
            user.setProfilePhoto(null);
            user.setProfilePhotoId(null);
            userRepository.save(user);
            this.deleteUserCache(user);
        }
    }

    public ProfileDTO saveProfile(ProfileDTO profileDTO) {
        User user = profileDTO.getId() != null
            ? userRepository.findById(profileDTO.getId()).orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "id", profileDTO.getId()))
            : getCurrentUser();
        if (Stage.COMPLETED_REGISTRATION.equals(user.getStage())) {
            throw new BadRequestException(localizationService.localize("user.profile.already.created"));
        }

        user.setFirstName(profileDTO.getFirstName());
        user.setLastName(profileDTO.getLastName());
        user.setPatronymic(profileDTO.getPatronymic());
        user.setStage(Stage.COMPLETED_PROFILE);
        userRepository.save(user);
        this.deleteUserCache(user);
        return profileDTO;
    }

    public ProfileDetailDTO getProfile() {
        User user = getCurrentUser();
        ProfileDetailDTO profileDetailDTO = new ProfileDetailDTO();
        profileDetailDTO.setId(user.getId());
        profileDetailDTO.setPhone(user.getLogin());
        profileDetailDTO.setFirstName(user.getFirstName());
        profileDetailDTO.setLastName(user.getLastName());
        profileDetailDTO.setPatronymic(user.getPatronymic());
        profileDetailDTO.setCompanyId(user.getCompanyId());
        profileDetailDTO.setStage(user.getStage());
        List<Authority> authorities = new ArrayList<>(user.getAuthorities());
        profileDetailDTO.setRole(Role.getByCode(authorities.get(0).getName()));
        if (user.getProfilePhotoId() != null) {
            FileUpload fileUpload = fileUploadRepository.findById(user.getProfilePhotoId()).orElseThrow(notFoundExceptionThrow(FileUpload.class.getSimpleName(), "id", user.getProfilePhotoId()));
            profileDetailDTO.setProfileImage(fileUpload.getFileDTO());
        }
        return profileDetailDTO;
    }

    public UserIdDTO getActiveClient(String phone) {
        User user = userRepository.findFirstByLoginAndDeletedIsFalse(phone)
            .orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "login", phone));
        return new UserIdDTO(user.getId());
    }

    public ProfileDetailDTO updateProfile(ProfileDetailDTO profileDetailDTO) {

        User user = userRepository.findById(SecurityUtils.getCurrentUserId())
            .orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "id", SecurityUtils.getCurrentUserLogin().orElse(null)));

        if (profileDetailDTO.getFirstName() != null) {
            user.setFirstName(profileDetailDTO.getFirstName());
        }
        if (profileDetailDTO.getLastName() != null) {
            user.setLastName(profileDetailDTO.getLastName());
        }

        if (profileDetailDTO.getPatronymic() != null) {
            user.setPatronymic(profileDetailDTO.getPatronymic());
        }
        userRepository.save(user);
        this.deleteUserCache(user);
        return profileDetailDTO;
    }

    public void updateLanguage(String code) {
        User user = this.getCurrentUser();
        user.setLanguage(LanguageEnum.get(code));
        userRepository.save(user);
        cacheService.deleteUserCache(user.getLogin());
    }

    public void completeRegistration(String phone) {
        User user = phone != null
            ? userRepository
            .findOneWithAuthoritiesByLoginAndDeletedFalse(phone)
            .orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "login", null))
            : getCurrentUser();
        Company company = companyRepository
            .findById(user.getCompanyId())
            .orElseThrow(notFoundExceptionThrow(Company.class.getSimpleName(), "id", user.getCompanyId()));

        user.setStage(Stage.COMPLETED_REGISTRATION);
        userRepository.save(user);
        this.deleteUserCache(user);

        company.setStatus(CommonStatus.ACTIVE);
        companyRepository.save(company);
    }
}

