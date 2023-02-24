package uz.uzkassa.smartposrestaurant.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.OrderStatus;
import uz.uzkassa.smartposrestaurant.enums.PaymentStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.25.2022 11:04
 */

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailDTO extends GrandTotalDTO implements Serializable {

    String id;

    CommonDTO user;

    LocalDateTime orderDate;

    OrderStatus status;

    CommonDTO branch;

    String orderNumber;

    PaymentStatus paymentStatus;

    List<OrderItemDetailDTO> orderItems = new ArrayList<>();

    @JsonIgnore
    public void addItem(OrderItemDetailDTO orderItem) {
        orderItems.add(orderItem);
    }
}
