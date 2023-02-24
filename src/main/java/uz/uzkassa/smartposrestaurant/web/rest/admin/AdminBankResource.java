package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.service.BankService;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.10.2022 11:25
 */
@RestController
@RequestMapping(ApiConstants.adminBankRootApi)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AdminBankResource {

    BankService bankService;

    @GetMapping(ApiConstants.sync)
    public ResponseEntity<Void> sync() {
        bankService.sync();
        return ResponseEntity.ok().build();
    }
}
