package uz.uzkassa.smartposrestaurant.web.rest.pub;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.address.RegionDistrictDTO;
import uz.uzkassa.smartposrestaurant.service.RegionService;

import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022
 */
@RestController
@RequestMapping(ApiConstants.publicRegionRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PublicRegionResource {

    RegionService regionService;

    @GetMapping(ApiConstants.items)
    public ResponseEntity<List<RegionDistrictDTO>> getItems() {
        return ResponseEntity.ok().body(regionService.getItems());
    }
}
