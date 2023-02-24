package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Powered by Shuxratjon Rayimjonov
 * Date: 06.10.2022 17:38
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EntityTypeEnum implements Serializable {
    COMPANY {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case cyrillic:
                    return "Компания";
                case uz:
                    return "Kompaniya";
                case en:
                    return "Company";
                default:
                    return "Компания";
            }
        }
    },
    POST {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                case en:
                    return "Post";
                default:
                    return "Пост";
            }
        }
    },
    USER {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case cyrillic:
                    return "Фойдаланувчи";
                case uz:
                    return "Foydalanuvchi";
                case en:
                    return "User";
                default:
                    return "Пользователь";
            }
        }
    };

    private final String code;
    private final String name;

    EntityTypeEnum() {
        this.code = this.name();
        this.name = getName();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public EntityTypeEnum forValue(Map<String, String> value) {
        return EntityTypeEnum.valueOf(value.get("code"));
    }
}
