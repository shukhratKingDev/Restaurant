package uz.uzkassa.smartposrestaurant.web.rest.pub;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.service.ActivityTypeService;

import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.10.2022 10:36
 */
@RestController
@RequestMapping(ApiConstants.publicActivityTypeRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PublicActivityTypeResource {

    ActivityTypeService activityTypeService;

    @GetMapping(ApiConstants.lookUp)
    public ResponseEntity<List<CommonDTO>> lookUp(BaseFilter filter) {
        return ResponseEntity.ok().body(activityTypeService.lookUp(filter));
    }
}
