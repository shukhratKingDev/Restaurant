package uz.uzkassa.smartposrestaurant.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.OrderStatus;

import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.25.2022 13:14
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderListDTO extends GrandTotalDTO {

    String id;

    CommonDTO user;

    CommonDTO company;

    LocalDateTime orderDate;

    OrderStatus status;

    String orderNumber;

    CommonDTO branch;
}
