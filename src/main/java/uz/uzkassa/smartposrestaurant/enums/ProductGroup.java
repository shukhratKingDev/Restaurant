package uz.uzkassa.smartposrestaurant.enums;

import lombok.Getter;

import java.io.Serializable;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 08.11.2022 17:08
 */
@Getter
public enum ProductGroup implements Serializable {
    FOOD("food", 1, 1, "Food", ProductType.FOOD);

    private final String extension;
    private final Integer productGroup;
    private final Integer markingCode;
    private final String name;
    private final ProductType[] productTypes;

    ProductGroup(String extension, Integer productGroup, Integer markingCode, String name, ProductType... productTypes) {
        this.extension = extension;
        this.productGroup = productGroup;
        this.markingCode = markingCode;
        this.name = name;
        this.productTypes = productTypes;
    }

}
