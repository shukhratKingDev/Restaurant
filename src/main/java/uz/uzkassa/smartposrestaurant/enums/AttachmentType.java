package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.30.2022 18:06
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AttachmentType implements Serializable {
    TEXT {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Matn";
                case cyrillic:
                    return "Матн";
                case en:
                    return "Text";
                default:
                    return "Текcт";
            }
        }
    },
    PHOTO {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Rasm";
                case cyrillic:
                    return "Расм";
                case en:
                    return "Photo";
                default:
                    return "Фото";
            }
        }

    },
    PHOTO_GALLERY {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Galereya";
                case en:
                    return "Photo-Gallery";
                default:
                    return "Галерея";
            }
        }
    },
    VIDEO {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                case en:
                    return "Video";
                default:
                    return "Видео";
            }
        }
    },
    ADDRESS {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Manzil";
                case cyrillic:
                    return "Манзил";
                case en:
                    return "Address";
                default:
                    return "Адрес";
            }
        }
    };

    private final String name;
    private final String code;

    AttachmentType() {
        this.code = this.name();
        this.name = getName();
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    public static AttachmentType get(String folderType) {
        return AttachmentType.valueOf(folderType);
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static AttachmentType forValue(Map<String, String> value) {
        return AttachmentType.valueOf(value.get("code"));
    }
}
