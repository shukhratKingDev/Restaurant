package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 06.10.2022 18:21
 */

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BusinessType implements Serializable {

    OOO {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "OOO";
            }
            return "ООО";
        }
    },
    CHP {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "CHP";
            }
            return "ЧП";
        }
    },
    IP {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "IP";
            }
            return "ИП";
        }
    },
    AO {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "AO";
            }
            return "АО";
        }
    },
    ZAO {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "ZAO";
            }
            return "ЗАО";
        }
    },
    SP {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "SP";
            }
            return "СП";
        }
    },
    DP {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "DP";
            }
            return "ДП";
        }
    },
    DX {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "DX";
            }
            return "ДХ";
        }
    },
    FX {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "FX";
            }
            return "ФХ";
        }
    },
    UP {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "UP";
            }
            return "УП";
        }
    },
    NOU {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "NOU";
            }
            return "НОУ";
        }
    },

    CHMP {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "CHMP";
            }
            return "ЧМП";
        }
    },
    PK {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "PK";
            }
            return "ПК";
        }
    },
    GUP {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "GUP";
            }
            return "ГУП";
        }
    },

    TCHSJ {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "TCHSJ";
            }
            return "ТЧСЖ";
        }
    },

    SP_OOO {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "SP_OOO";
            }
            return "СП OOO";
        }
    },
    GU {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "GU";
            }
            return "ГУ";
        }
    },
    IP_OOO {
        @Override
        public String getName(LanguageEnum language) {
            if (language == LanguageEnum.uz) {
                return "Ip OOO";
            }
            return "ИП ООО";
        }
    },
    DUO {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Maktabgacha ta'lim muassasasi";
                case cyrillic:
                    return "Мактабгача таълим муассасаси";
                default:
                    return "Дошкольное образовательное учреждение";
            }
        }
    },
    RG {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Ro`znoma tahririyati";
                case cyrillic:
                    return "Рузнома таҳририяти";
                default:
                    return "Редакция газеты";
            }
        }
    },
    ODO {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Qo'shimcha mas'uliyatli jamiyat";
                case cyrillic:
                    return "Қўшимча масъулиятли жамият";
                default:
                    return "Общество с дополнительной ответственностью";
            }
        }
    };


    private final String name;
    private final String code;

    BusinessType() {
        this.name = getName();
        this.code = this.name();
    }

    public abstract String getName(LanguageEnum language);
    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static BusinessType forValue(Map<String, String> value) {
        return BusinessType.valueOf(value.get("code"));
    }


}
