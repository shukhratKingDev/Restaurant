package uz.uzkassa.smartposrestaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.order.GrandTotalDTO;
import uz.uzkassa.smartposrestaurant.enums.PaymentStatus;
import uz.uzkassa.smartposrestaurant.enums.PaymentType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.24.2022 19:52
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderDTO extends GrandTotalDTO {

    String id;

    String userId;

    String branchId;

    PaymentStatus paymentStatus;

    PaymentType paymentType;

    List<OrderItemDTO> orderItems = new ArrayList<>();

    @JsonIgnore
    public void add(OrderItemDTO orderItem) {
        orderItems.add(orderItem);
    }
}

