package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.Locale;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 15:36
 */

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LocalizationService {

    @NonNull
    MessageSource messageSource;

    public String localize(String code) {
        return this.localize(code, null, code, SecurityUtils.getLocale());
    }

    public String localize(String code, Object[] params) {
        return this.localize(code, params, SecurityUtils.getLocale());
    }

    public String localize(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, SecurityUtils.getLocale());
    }

    public String localize(String code, Object[] params, Locale locale) {
        return this.localize(code, params, code, locale);
    }

    public String localize(String code, Object[] params, String defaultMessage, Locale locale) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return messageSource.getMessage(code, params, defaultMessage, locale);
    }

}
