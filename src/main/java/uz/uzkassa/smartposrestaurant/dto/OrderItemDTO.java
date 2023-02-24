package uz.uzkassa.smartposrestaurant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.24.2022 19:40
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderItemDTO {

    String id;

    String orderItemId;

    String unitId;

    BigDecimal qty;

    BigDecimal price;

    BigDecimal totalPrice;
}
