package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.Reservation;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.filters.ReservationFilter;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.29.2022 16:24
 */
public interface ReservationRepositoryCustom {
    ResultList<Reservation> getResultList(ReservationFilter filter);
}
