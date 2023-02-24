package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.24.2022 19:24
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentStatus implements Serializable {
    NOT_PAID {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "To'lanmadi";
                case cyrillic:
                    return "Тўланмади";
                case en:
                    return "Not paid";
                default:
                    return "Не оплачено";
            }
        }
    },

    PARTIALLY_PAID {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Qisman to`langan";
                case cyrillic:
                    return "Қисман тўланган";
                case en:
                    return "Partially paid";
                default:
                    return "Частично оплачено";
            }
        }
    },

    PAID {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "To'langan";
                case cyrillic:
                    return "Тўланган";
                case en:
                    return "Paid";
                default:
                    return "Оплачен";
            }
        }
    };

    private final String name;
    private final String code;

    PaymentStatus() {
        this.code = this.name();
        this.name = this.getName();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static PaymentStatus forValue(Map<String, String> value) {
        return PaymentStatus.valueOf(value.get("code"));
    }
}
