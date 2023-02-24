package uz.uzkassa.smartposrestaurant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.order.OrderDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.reservation.ClientDTO;

import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.29.2022 14:27
 */
@Getter
@Setter
@NoArgsConstructor
public class ReservationDetailDTO {

    CommonDTO branch;

    Integer numberOfGuest;

    LocalDateTime reservationDate;

    ClientDTO client;

    CommonDTO order;

}
