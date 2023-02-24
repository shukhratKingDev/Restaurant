package uz.uzkassa.smartposrestaurant.dto.reservation;

import lombok.Getter;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;

import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.29.2022 16:19
 */
@Getter
@Setter
public class ReservationListDTO {

    String id;

    CommonDTO client;

    CommonDTO branch;

    Integer numberOfGuest;

    LocalDateTime reservationDate;

    CommonDTO order;
}
