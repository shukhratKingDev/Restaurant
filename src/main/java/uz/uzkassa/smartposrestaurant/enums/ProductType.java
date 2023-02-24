package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 19:16
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ProductType implements Serializable {

    FOOD(1002, 13, ProductGroup.FOOD) {
        @Override
        public String getName(LanguageEnum language) {
            switch (language) {
                case uz:
                    return "Ovqat";
                case cyrillic:
                    return "Овқат";
                default:
                    return "Еда";
            }
        }
    };

    private final String name;
    private final String code;
    private final int codeNumber;
    private final Integer templateId;
    private final ProductGroup productGroup;

    ProductType(int codeNumber, Integer templateId, ProductGroup productGroup) {
        this.code = name();
        this.codeNumber = codeNumber;
        this.templateId = templateId;
        this.name = this.getName();
        this.productGroup = productGroup;
    }

    public abstract String getName(LanguageEnum language);

    public String getName() {
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static ProductType forValue(Map<String, String> value) {
        return ProductType.valueOf(value.get("code"));
    }

}
