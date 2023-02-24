package uz.uzkassa.smartposrestaurant.web.rest.pub;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.address.RegionDistrictDTO;
import uz.uzkassa.smartposrestaurant.service.DistrictService;

import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.10.2022 18:01
 */
@RestController
@RequestMapping(ApiConstants.publicDistrictRootApi)
public class PublicDistrictResource {

    private final DistrictService districtService;

    public PublicDistrictResource(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping("/items/{regionId}")
    public ResponseEntity<List<RegionDistrictDTO>> getItemsByRegionId(@PathVariable String regionId) {
        return ResponseEntity.ok().body(districtService.getItemsByRegionId(regionId));
    }
}
