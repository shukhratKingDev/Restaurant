package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Powered by Shuxratjon Rayimjonov
 * Date: 06.10.2022 17:17
 */

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Vat implements Serializable {

    WITHOUT_VAT(null) {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "QQS yo'q";
                case cyrillic:
                    return "ҚҚС йўқ";
                default:
                    return "Без НДС";
            }
        }
    },
    VAT_0(BigDecimal.ZERO) {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "QQS 0%";
                case cyrillic:
                    return "ҚҚС 0%";
                default:
                    return "НДС 0%";
            }
        }
    },
    VAT_15(new BigDecimal("15")) {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "QQS 15%";
                case cyrillic:
                    return "ҚҚС 15%";
                default:
                    return "НДС 15%";
            }
        }
    };

    private final String name;
    private final String code;
    private final BigDecimal amount;

    Vat(BigDecimal amount) {
        this.code = name();
        this.name = getName();
        this.amount = amount;
    }

    public static Vat getByAmount(BigDecimal amount) {
        if (amount == null) {
            return Vat.WITHOUT_VAT;
        } else if (amount.compareTo(Vat.VAT_15.getAmount()) == 0) {
            return Vat.VAT_15;
        } else {
            return Vat.WITHOUT_VAT;
        }
    }

    public abstract String getName(LanguageEnum languageEnum);
}
