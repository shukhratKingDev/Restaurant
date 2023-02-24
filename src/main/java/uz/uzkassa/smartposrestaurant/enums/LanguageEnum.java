package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 06.10.2022 16:04
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LanguageEnum implements Serializable {
    uz("uz", "O'zbek"),
    cyrillic("cyrillic", "Узбек"),
    en("en", "English"),
    ru("ru", "Русский");


    private final String code;
    private final String name;

    LanguageEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static LanguageEnum get(String code) {
        if (StringUtils.isEmpty(code)) {
            return getDefaultLanguage();
        }
        for (LanguageEnum statusEnum : LanguageEnum.values()) {
            if (code.equals(statusEnum.getCode())) {
                return statusEnum;
            }
        }
        return getDefaultLanguage();
    }

    public static LanguageEnum getDefaultLanguage() {
        return LanguageEnum.ru;
    }
}
