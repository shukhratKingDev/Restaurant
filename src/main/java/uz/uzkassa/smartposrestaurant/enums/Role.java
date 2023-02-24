package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonCodeDTO;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 06.10.2022 16:01
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum Role {

    SUPER_ADMIN("ROLE_SUPER_ADMIN") {
        @Override
        public String getName(LanguageEnum languageEnum) {
            switch (languageEnum) {
                case uz:
                    return "Super Admin";
                case cyrillic:
                    return "Супер Админ";
                case ru:
                    return "Супер администратор";
                default:
                    return "Супер администратор";
            }

        }
    },

    ADMIN("ROLE_ADMIN") {
        @Override
        public String getName(LanguageEnum languageEnum) {
            switch (languageEnum) {
                case uz:
                    return "Admin";
                case cyrillic:
                    return "Админ";
                case ru:
                    return "Администратор";
                default:
                    return "Администратор";
            }
        }
    },
    MODERATOR("ROLE_MODERATOR") {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Moderator";
                case cyrillic:
                    return "Модератор";
                default:
                    return "Модератор";
            }
        }
    },
    OPERATOR("ROLE_OPERATOR") {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Operator";
                case cyrillic:
                    return "Оператор";
                default:
                    return "Оператор";
            }
        }
    },
    SUPPORT("ROLE_SUPPORT") {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Texnik mutaxassis";
                case cyrillic:
                    return "Техник мутахассис";
                default:
                    return "Технический специалист";
            }
        }
    },
    BUSINESS_OWNER("ROLE_BUSINESS_OWNER") {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Biznes egasi";
                case cyrillic:
                    return "Бизнес эгаси";
                default:
                    return "Владелец бизнеса";
            }
        }
    },
    DIRECTOR("ROLE_DIRECTOR") {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Direktor";
                case cyrillic:
                    return "Директор";
                default:
                    return "Директор";
            }
        }
    },
    ACCOUNTANT("ROLE_ACCOUNTANT") {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Hisobchi";
                case cyrillic:
                    return "Ҳисобчи";
                default:
                    return "Бухгалтер";
            }
        }
    },
    BRANCH_ADMIN("ROLE_BRANCH_ADMIN") {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Filial menejeri";
                case cyrillic:
                    return "Филиал менежери";
                default:
                    return "Руководитель филиала";
            }
        }
    },
    WAREHOUSE_MANAGER("ROLE_WAREHOUSE_MANAGER") {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Sklad menejeri";
                case cyrillic:
                    return "Склад менежери";
                default:
                    return "Заведующий складом";
            }
        }
    },
    MANAGER("ROLE_MANAGER") {
        @Override
        public String getName(LanguageEnum languageEnum) {
            switch (languageEnum) {
                case uz:
                    return "Ish boshqaruvchi";
                case cyrillic:
                    return "Иш бошқарувчи";
                case en:
                    return "Manager";
                default:
                    return "Менеджер";
            }
        }
    },
    CASHIER("ROLE_CASHIER") {
        @Override
        public String getName(LanguageEnum languageEnum) {
            switch (languageEnum) {
                case uz:
                    return "Kassir";
                case cyrillic:
                    return "Кассир";
                case en:
                    return "Cashier";
                default:
                    return "Касса";
            }
        }
    },
    WAITRESS("ROLE_WAITRESS") {
        @Override
        public String getName(LanguageEnum languageEnum) {
            switch (languageEnum) {
                case uz:
                    return "Ofitsant";
                case cyrillic:
                    return "Официант";
                case en:
                    return "Waitress";
                default:
                    return "Официант";
            }
        }
    },
    CHEF("ROLE_CHEF") {
        @Override
        public String getName(LanguageEnum languageEnum) {
            switch (languageEnum) {
                case uz:
                    return "Oshpaz";
                case cyrillic:
                    return "Ошпаз";
                case en:
                    return "Chef";
                default:
                    return "Повар";
            }
        }
    },
    BARMEN("ROLE_BARMEN") {
        @Override
        public String getName(LanguageEnum languageEnum) {
            switch (languageEnum) {
                case uz:
                    return "Bar xodimi";
                case cyrillic:
                    return "Бар ходими";
                case en:
                    return "Barmen";
                default:
                    return "Бармен";
            }
        }
    },
    COURIER("ROLE_COURIER") {
        @Override
        public String getName(LanguageEnum languageEnum) {
            switch (languageEnum) {
                case uz:
                    return "Yetkazib beruvchi";
                case cyrillic:
                    return "Етказиб берувчи";
                case en:
                    return "Courier";
                default:
                    return "Курьер";
            }
        }
    },
    CONFIDANT("ROLE_CONFIDANT") {
        @Override
        public String getName(LanguageEnum language) {
            return "Доверенное лицо";
        }
    },
    ANONYMOUS("ROLE_ANONYMOUS") {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Anonim";
                case cyrillic:
                    return "Аноним";
                default:
                    return "Аноним";
            }
        }
    },
    CLIENT("ROLE_CLIENT") {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Mijoz";
                case cyrillic:
                    return "Мижоз";
                case en:
                    return "Client";
                default:
                    return "Клиент";
            }
        }
    };


    private final String name;
    private final String code;

    Role(String code) {
        this.name = this.getName();
        this.code = code;
    }

    public static List<CommonCodeDTO> getOrganizationCodesAsCommonDTO() {
        return Arrays.asList(
            new CommonCodeDTO(null, Role.ADMIN.getName(), Role.ADMIN.getCode()),
            new CommonCodeDTO(null, Role.MODERATOR.getName(), Role.MODERATOR.getCode()),
            new CommonCodeDTO(null, Role.OPERATOR.getName(), Role.OPERATOR.getCode()),
            new CommonCodeDTO(null, Role.SUPPORT.getName(), Role.SUPPORT.getCode())
        );
    }

    public abstract String getName(LanguageEnum languageEnum);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    public static Role getByCode(String code) {
        for (Role role : Role.values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return null;
    }

    public static List<String> getAdminRoles() {
        return Arrays.asList(
            Role.SUPER_ADMIN.getCode(),
            Role.ADMIN.getCode(),
            Role.MODERATOR.getCode(),
            Role.SUPPORT.getCode(),
            Role.OPERATOR.getCode()
        );
    }

    public static List<String> getCabinetRoles() {
        return Arrays.asList(
            Role.BUSINESS_OWNER.getCode(),
            Role.BRANCH_ADMIN.getCode(),
            Role.CONFIDANT.getCode(),
            Role.ACCOUNTANT.getCode(),
            Role.DIRECTOR.getCode(),
            Role.WAREHOUSE_MANAGER.getCode(),
            Role.ADMIN.getCode()
            );
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static Role forCodeValue(Map<String, String> value) {
        String roleCode = value.get("code");
        for (Role role : values()) {
            if (role.getCode().equals(roleCode) || role.name().equals(roleCode)) {
                return role;
            }
        }
        return null;
    }
}
