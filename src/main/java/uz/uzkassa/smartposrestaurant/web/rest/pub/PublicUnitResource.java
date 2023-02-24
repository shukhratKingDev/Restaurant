package uz.uzkassa.smartposrestaurant.web.rest.pub;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.UnitDTO;
import uz.uzkassa.smartposrestaurant.service.UnitService;

import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 17: 09
 */
@RestController
@RequestMapping(ApiConstants.publicUnitRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PublicUnitResource {

    UnitService unitService;

    @GetMapping(ApiConstants.items)
    public ResponseEntity<List<UnitDTO>> getItems() {
        return ResponseEntity.ok().body(unitService.getItems());
    }
}
