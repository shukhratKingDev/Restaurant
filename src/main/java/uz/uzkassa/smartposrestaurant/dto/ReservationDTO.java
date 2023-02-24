package uz.uzkassa.smartposrestaurant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.uzkassa.smartposrestaurant.dto.reservation.ClientDTO;

import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.28.2022 19:45
 */
@Getter
@Setter
@NoArgsConstructor
public class ReservationDTO {

    ClientDTO client;

    String branchId;

    Integer numberOfGuest;

    LocalDateTime reservationDate;

    OrderDTO order;
}
