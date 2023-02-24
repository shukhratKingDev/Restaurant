package uz.uzkassa.smartposrestaurant.web.rest.cabinet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.ReservationDTO;
import uz.uzkassa.smartposrestaurant.dto.ReservationDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.reservation.ReservationListDTO;
import uz.uzkassa.smartposrestaurant.filters.ReservationFilter;
import uz.uzkassa.smartposrestaurant.service.ReservationService;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.ArrayList;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.29.2022 13:50
 */
@RestController
@RequestMapping(ApiConstants.cabinetReservationRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class CabinetReservationResource {

    ReservationService reservationService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok().body(reservationService.create(reservationDTO));
    }

    @GetMapping(ApiConstants.id)
    public ResponseEntity<ReservationDetailDTO> get(@PathVariable String id) {
        return ResponseEntity.ok().body(reservationService.get(id));
    }

    @PutMapping(ApiConstants.id)
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok().body(reservationService.update(id, reservationDTO));
    }

    @GetMapping
    public ResponseEntity<Page<ReservationListDTO>> getList(ReservationFilter filter) {
        if (SecurityUtils.getCurrentBranchId() == null) {
            return ResponseEntity.ok(new PageImpl<>(new ArrayList<>(), filter.getPageable(), 0L));
        }
        return ResponseEntity.ok(reservationService.getList(filter));
    }

    @DeleteMapping(ApiConstants.id)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reservationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
