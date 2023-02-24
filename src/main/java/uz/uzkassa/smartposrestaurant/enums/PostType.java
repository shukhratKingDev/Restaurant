package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 15:05
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PostType implements Serializable {
    EVENT {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Tadbir";
                case cyrillic:
                    return "Тадбир";
                case en:
                    return "Event";
                default:
                    return "Мероприятие";
            }
        }
    },
    ARTICLE {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Maqola";
                case cyrillic:
                    return "Мақола";
                default:
                    return "Статья";
            }
        }
    },
    NEWS {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Yangilik";
                case cyrillic:
                    return "Янгилик";
                default:
                    return "Новости";
            }
        }
    };

    private final String name;
    private final String code;

    PostType() {
        this.code = this.name();
        this.name = this.getName();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static PostType forValue(Map<String, String> value) {
        return PostType.valueOf(value.get("code"));
    }
}
