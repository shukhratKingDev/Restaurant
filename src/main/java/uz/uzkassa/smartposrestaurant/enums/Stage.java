package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 26.10.2022 17:06
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Stage {

    PARTIAL_SIGNED {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Qisman imzolangan";
            }
            return "Частично подписано";
        }
    },
    REGISTERED {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Ro'yxatga olingan";
            }
            return "Зарегистрирован";
        }
    },
    COMPLETED_PROFILE {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Profil to'ldirilgan";
            }
            return "Заполнил профиль";
        }
    },
    COMPANY_PARTIALLY_REGISTERED {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Kompaniya qisman ro'yxatdan o'tdi";
            }
            return "Компания частично зарегистрирована";
        }
    },
    COMPANY_DOCUMENT_PARTIALLY_UPLOADED {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Kompaniya hujjatlari qisman yuklandi";
            }
            return "Документ компании загружен частично";
        }
    },
    COMPANY_DOCUMENT_UPLOADED {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Kompaniya hujjatlari to'liq yuklandi";
            }
            return "Документ компании загружен";
        }
    },
    COMPANY_BANK_ADDED {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Kompaniyaga bank qo'shildi";
            }
            return "Добавлен банк компании";
        }
    },
    COMPANY_MERCHANT_ADDED {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Kompaniyaga merchant qo'shildi";
            }
            return "Добавлен продавец компании";
        }
    },
    COMPANY_TARIFF_ADDED {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Kompaniyaga tarif qo'shildi";
            }
            return "Тариф добавлен в компанию";
        }
    },

    COMPLETED_REGISTRATION {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Ro'yxatdan butunlay o'tilgan";
            }
            return "Регистрация завершена";
        }
    },
    IDENTIFIED {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Aniqlangan";
            }
            return "Идентифицированный";
        }
    };

    private final String name;
    private final String code;

    Stage() {
        this.code = this.name();
        this.name = this.getName();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static Stage forValue(Map<String, String> value) {
        return Stage.valueOf(value.get("code"));
    }

    public static Stage get(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (Stage stage : Stage.values()) {
            if (code.equals(stage.getCode())) {
                return stage;
            }
        }
        return null;
    }
}
