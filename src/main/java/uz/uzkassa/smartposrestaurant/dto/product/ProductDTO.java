package uz.uzkassa.smartposrestaurant.dto.product;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.enums.ProductType;
import uz.uzkassa.smartposrestaurant.enums.Vat;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 16:01
 */
@Getter
@Setter
public class ProductDTO implements Serializable {

    String id;

    String name;

    String barCode;

    String aggregationCode;

    String markCode;

    String categoryId;

    String branchId;

    ProductType productType;

    Vat vat;

    BigDecimal price;

    boolean hasMark;

    String vatBarCode;
    String catalogName;

    String packageName;
    String packageCode;
    BigDecimal qtyInPackage;

    String capacity;

    String companyId;
}
