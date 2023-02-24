package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 12:12
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonStatus implements Serializable {
    ACTIVE {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Faol";
                case cyrillic:
                    return "Фаол";
                case en:
                    return "Active";
                default:
                    return "Активный";
            }
        }
    },
    IN_ACTIVE {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Faol emas";
                case cyrillic:
                    return "Фаол эмас";
                case en:
                    return "Inactive";
                default:
                    return "Неактивный";
            }
        }
    };

    private final String name;
    private final String code;

    CommonStatus() {
        this.code = this.name();
        this.name = this.getName();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public CommonStatus forValue(Map<String, String> value) {
        return CommonStatus.valueOf(value.get("code"));
    }

}
