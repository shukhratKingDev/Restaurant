package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.service.ActivityTypeService;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.10.2022 10:36
 */
@RestController
@RequestMapping(ApiConstants.adminActivityTypeRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AdminActivityTypeResource {

    ActivityTypeService activityTypeService;

    @GetMapping(ApiConstants.sync)
    public ResponseEntity<Void> sync() {
        activityTypeService.sync();
        return ResponseEntity.ok().build();
    }
}
