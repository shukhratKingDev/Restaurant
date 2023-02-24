package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.24.2022 18:54
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrderStatus {
    NEW {
        @Override
        public String getName(LanguageEnum language) {
            switch (language){
                case uz:
                    return "Yangi";
                case cyrillic:
                    return "Қабул қилинди";
                case ru:
                    return "Полученный";
                case en:
                    return "New";
                default:
                    return "Полученный";
            }
        }
    },
    RECEIVED {
        @Override
        public String getName(LanguageEnum language) {
            switch (language){
                case uz:
                    return "Qabul qilindi";
                case cyrillic:
                    return "Қабул қилинди";
                case ru:
                    return "Полученный";
                case en:
                    return "Received";
                default:
                    return "Полученный";
            }
        }
    },
    PREPARING {
        @Override
        public String getName(LanguageEnum language) {
            switch (language){
                case uz:
                    return "Tayyorlanmoqda";
                case cyrillic:
                    return "Тайёрланмоқда";
                case ru:
                    return "Подготовка";
                case en:
                    return "Preparing";
                default:
                    return "Подготовка";
            }
        }

    },
    READY {
        @Override
        public String getName(LanguageEnum language) {
            switch (language){
                case uz:
                    return "Tayyor";
                case cyrillic:
                    return "Тайёр";
                case ru:
                    return "Готовый";
                case en:
                    return "Ready";
                default:
                    return "Готовый";
            }
        }

    },
    CANCELLED {
        @Override
        public String getName(LanguageEnum language) {
            switch (language){
                case uz:
                    return "Bekor qilindi";
                case cyrillic:
                    return "Бекор қилинди";
                case ru:
                    return "Отменено";
                case en:
                    return "Cancelled";
                default:
                    return "Отменено";
            }
        }

    };

    private final String name;

    private final String code;

    OrderStatus() {
        this.code = this.name();
        this.name = this.getName();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static OrderStatus forValue(Map<String, String> value) {
        return OrderStatus.valueOf(value.get("code"));
    }
}
