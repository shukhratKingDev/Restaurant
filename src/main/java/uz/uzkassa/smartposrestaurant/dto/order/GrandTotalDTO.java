package uz.uzkassa.smartposrestaurant.dto.order;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.25.2022 10:35
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PROTECTED)
public class GrandTotalDTO implements Serializable {

    BigDecimal totalQty;

    BigDecimal totalPrice;
}
