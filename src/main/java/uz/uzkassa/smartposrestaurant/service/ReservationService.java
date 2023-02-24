package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.domain.Reservation;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.dto.ReservationDTO;
import uz.uzkassa.smartposrestaurant.dto.ReservationDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.dto.reservation.ClientDTO;
import uz.uzkassa.smartposrestaurant.dto.reservation.ReservationListDTO;
import uz.uzkassa.smartposrestaurant.filters.ReservationFilter;
import uz.uzkassa.smartposrestaurant.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.29.2022 13: 52
 */
@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ReservationService extends BaseService {

    ReservationRepository reservationRepository;
    OrderService orderService;
    UserService userService;

    public String create(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setBranchId(getCurrentBranchId());
        if (reservationDTO.getClient().getId() != null) {
            reservation.setClientId(reservationDTO.getClient().getId());
        } else {
            User client = userRepository.findOneByLoginAndDeletedIsFalse(reservationDTO.getClient().getPhone()).orElse(userService.createClient(reservationDTO.getClient()));
            reservation.setClientId(client.getId());
        }
        reservation.setNumberOfGuest(reservationDTO.getNumberOfGuest());
        reservationDTO.setReservationDate(reservationDTO.getReservationDate());
        if (reservationDTO.getOrder() != null) {
            reservationDTO.getOrder().setBranchId(reservation.getBranchId());
            reservationDTO.getOrder().setUserId(reservation.getClientId());
            reservation.setOrderId(orderService.create(reservationDTO.getOrder()));
        }
        reservationRepository.save(reservation);
        return reservation.getId();
    }

    @Transactional(readOnly = true)
    public ReservationDetailDTO get(String id) {
        return reservationRepository.findById(id).map(reservation -> {
            ReservationDetailDTO reservationDetailDTO = new ReservationDetailDTO();
            reservationDetailDTO.setBranch(reservation.getBranch().toCommonDTO());
            reservationDetailDTO.setReservationDate(reservation.getReservationDate());
            reservationDetailDTO.setClient(
                new ClientDTO(
                    reservation.getClientId(),
                    reservation.getClient().getPhone(),
                    reservation.getClient().getFirstName(),
                    reservation.getClient().getLastName()
                )
            );
            reservationDetailDTO.setReservationDate(reservation.getReservationDate());
            reservationDetailDTO.setNumberOfGuest(reservation.getNumberOfGuest());
            if (reservation.getOrder() != null) {
                reservationDetailDTO.setOrder(reservation.getOrder().toCommonDTO());
            }
            return reservationDetailDTO;
        }).orElseThrow(notFoundExceptionThrow(Reservation.class.getSimpleName(), "id", id));
    }

    public String update(String id, ReservationDTO reservationDTO) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setNumberOfGuest(reservationDTO.getNumberOfGuest());
            reservation.setReservationDate(reservationDTO.getReservationDate());
            if (reservationDTO.getOrder() != null) {
                orderService.update(reservation.getOrder().getId(), reservationDTO.getOrder());
            }
            reservationRepository.save(reservation);
            return reservation.getId();
        }).orElseThrow(notFoundExceptionThrow(Reservation.class.getSimpleName(), "id", id));
    }

    @Transactional(readOnly = true)
    public Page<ReservationListDTO> getList(ReservationFilter filter) {
        List<ReservationListDTO> result = new ArrayList<>();
        ResultList<Reservation> resultList = reservationRepository.getResultList(filter);
        resultList.getList()
            .forEach(reservation -> {
                ReservationListDTO reservationListDTO = new ReservationListDTO();
                reservationListDTO.setId(reservation.getId());
                reservationListDTO.setClient(reservation.getClient().toCommonDTO());
                reservationListDTO.setBranch(reservation.getBranch().toCommonDTO());
                reservationListDTO.setNumberOfGuest(reservation.getNumberOfGuest());
                reservationListDTO.setReservationDate(reservation.getReservationDate());
                if (reservation.getOrderId() != null) {
                    reservationListDTO.setOrder(reservation.getOrder().toCommonDTO());
                }
                result.add(reservationListDTO);
            });
        return new PageImpl<>(result, filter.getSortedPageable(), resultList.getCount());
    }

    public void delete(String id) {
        reservationRepository.deleteById(id);
    }
}
