package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.CheckCompanyDTO;
import uz.uzkassa.smartposrestaurant.service.CtsService;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 21:13
 */
@RestController
@RequestMapping(ApiConstants.adminCtsRootApi)
@Slf4j
public class AdminCtsResource {

    private final CtsService ctsService;

    public AdminCtsResource(CtsService ctsService) {
        this.ctsService = ctsService;
    }

    @GetMapping("/check/company/{tin}")
    public ResponseEntity<CheckCompanyDTO> checkCompany(@PathVariable String tin) {
        return ResponseEntity.ok().body(ctsService.checkCompany(tin));
    }
}
