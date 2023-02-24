package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 06.10.2022 19:09
 */

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserStatus implements Serializable {

    ACTIVE {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Faol";
                case cyrillic:
                    return "Фаол";
                default:
                    return "Актив";
            }
        }
    },

    PENDING {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Faollashmagan";
                case cyrillic:
                    return "Фаоллашмаган";
                default:
                    return "В ожидании";
            }
        }
    },

    BLOCKED {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Bloklangan";
                case cyrillic:
                    return "Блокланган";
                default:
                    return "Заблокировано";
            }
        }
    },

    INACTIVE {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Faol emas";
                case cyrillic:
                    return "Фаол эмас";
                default:
                    return "Неактивный";
            }
        }
    };


    private final String name;
    private final String code;

    UserStatus() {
        this.name = getName();
        this.code = name();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static UserStatus forValue(Map<String, String> value) {
        return UserStatus.valueOf(value.get("code"));
    }
}
