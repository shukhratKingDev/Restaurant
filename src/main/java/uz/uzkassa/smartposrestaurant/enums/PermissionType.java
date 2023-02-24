package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 11:18
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PermissionType {
    ADMIN {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                case en:
                    return "Admin";
                default:
                    return "Админ";
            }
        }
    },
    CABINET {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                case en:
                    return "Cabinet";
                default:
                    return "Cабинет";
            }
        }
    };

    private final String code;
    private final String name;

    PermissionType() {
        this.code = this.name();
        this.name = getName();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public PermissionType forValue(Map<String, String> value) {
        return PermissionType.valueOf(value.get("code"));
    }
}
