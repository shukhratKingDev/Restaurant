package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 14:50
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PostStatus implements Serializable {
    DRAFT {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Qoralama";
                case cyrillic:
                    return "Қоралама";
                default:
                    return "Черновик";
            }
        }
    },
    PUBLISHED {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Chop etilgan";
                case cyrillic:
                    return "Чоп этилган";
                default:
                    return "Опубликовано";
            }
        }
    };

    private final String name;
    private final String code;

    PostStatus() {
        this.name = this.name();
        this.code = this.getName();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }
}
