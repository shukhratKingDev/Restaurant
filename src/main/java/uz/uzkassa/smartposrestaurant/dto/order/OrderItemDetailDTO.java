package uz.uzkassa.smartposrestaurant.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.UnitDTO;

import java.math.BigDecimal;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.25.2022 11:23
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderItemDetailDTO {

    String orderItemId;

    String id;

    String name;

    UnitDTO unit;

    BigDecimal qty;

    BigDecimal price;

    BigDecimal totalPrice;

}
