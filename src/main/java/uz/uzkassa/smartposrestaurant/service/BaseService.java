package uz.uzkassa.smartposrestaurant.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import uz.uzkassa.smartposrestaurant.config.ApplicationProperties;
import uz.uzkassa.smartposrestaurant.constants.Constants;
import uz.uzkassa.smartposrestaurant.domain.Unit;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.company.Company;
import uz.uzkassa.smartposrestaurant.mappers.AddressMapper;
import uz.uzkassa.smartposrestaurant.repository.BranchRepository;
import uz.uzkassa.smartposrestaurant.repository.CompanyRepository;
import uz.uzkassa.smartposrestaurant.repository.UnitRepository;
import uz.uzkassa.smartposrestaurant.repository.UserRepository;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;
import uz.uzkassa.smartposrestaurant.web.rest.errors.BadRequestException;
import uz.uzkassa.smartposrestaurant.web.rest.errors.NotFoundException;

import java.text.DecimalFormat;
import java.util.function.Supplier;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:34
 */
@Component
@Slf4j
public abstract class BaseService implements Constants {

    @Autowired
    protected LocalizationService localizationService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    protected BranchRepository branchRepository;

    @Autowired
    protected ApplicationProperties applicationProperties;

    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected AddressMapper addressMapper;

    public void delete(String id) {

    }

    protected Supplier<NotFoundException> notFoundExceptionThrow(String entityName, String param, Object value) {
        return () ->
            new NotFoundException(
                localizationService.localize(
                    "entity.not.found.with.client",
                    new Object[]{localizationService.localize(entityName, entityName)}
                ),
                localizationService.localize("entity.not.found.with.dev", new Object[]{entityName, param, value})
            );
    }

    protected static DecimalFormat commonNumberingFormat = new DecimalFormat("000000");

    protected static String getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }

    protected String getCurrentCompanyId() {
        return SecurityUtils.getCurrentCompanyId();
    }

    @Transactional(readOnly = true)
    protected User getCurrentUser() {
        return userRepository
            .findById(SecurityUtils.getCurrentUserId())
            .orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "id", SecurityUtils.getCurrentUserId()));
    }

    protected String getCurrentBranchId() {
        return SecurityUtils.getCurrentBranchId();
    }

    @Transactional(readOnly = true)
    protected Unit getUnit() {
        return unitRepository.findFirstByCode(Unit.PC);
    }

    @Transactional(readOnly = true)
    protected boolean isFromAdmin() {
        return SecurityUtils.isFromAdmin();
    }

    @Transactional(readOnly = true)
    protected Company getCurrentCompany() {
        return companyRepository
            .findById(SecurityUtils.getCurrentCompanyId())
            .orElseThrow(notFoundExceptionThrow(Company.class.getSimpleName(), "id", SecurityUtils.getCurrentCompanyId()));
    }

    @Transactional(readOnly = true)
    protected String getCurrentCompanyTin() {
        return getCurrentCompany().getTin();
    }

    public void clearCache(String cacheName) {
        try {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        } catch (Exception e) {
            log.error("Clear cache: {}, error{}: ", cacheName, e.getMessage());
        }
    }

    public void evictCache(String cacheName, String key) {
        try {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.evict(key);
            }
        } catch (Exception e) {
            log.error("Evict cache: {}, key: {}, error {}: ", cacheName, key, e.getMessage());
        }
    }


    protected void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException(localizationService.localize("required", new Object[]{localizationService.localize("file")}));
        }

        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new BadRequestException(
                localizationService.localize("required", new Object[]{localizationService.localize("filename")})
            );
        }
    }
}
