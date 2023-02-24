package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.25.2022 15:07
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PaymentType {

    CARD {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Naqd pulsiz";
                case cyrillic:
                    return "Нақд пулсиз";
                case en:
                    return "Credit card";
                default:
                    return "Безналичными";
            }
        }
    },
    CASH {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Naqd";
                case cyrillic:
                    return "Нақд";
                case en:
                    return "Cash";
                default:
                    return "Наличными";
            }
        }
    },
    A_PAY {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "A-pay";
                case cyrillic:
                    return "А-пай";
                case en:
                    return "A-pay";
                default:
                    return "A-pay платеж";
            }
        }
    };

    private final String name;
    private final String code;

    PaymentType() {
        this.code = this.name();
        this.name = this.getName();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static PaymentType forValue(Map<String, String> value) {
        return PaymentType.valueOf(value.get("code"));
    }

}
