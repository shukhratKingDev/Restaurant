package uz.uzkassa.smartposrestaurant.dto.receipt;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.product.ProductDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 18:04
 */
@Getter
@Setter
public class ReceiptItemDTO implements Serializable {

    String id;

    ProductDTO productDTO;
}
