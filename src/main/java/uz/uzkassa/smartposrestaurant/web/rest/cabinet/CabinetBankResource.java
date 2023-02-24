package uz.uzkassa.smartposrestaurant.web.rest.cabinet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.bank.BankLookUpDTO;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.service.BankService;

import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.10.2022 11:25
 */
@RestController
@RequestMapping(ApiConstants.cabinetBankRootApi)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CabinetBankResource {

    BankService bankService;

    @GetMapping(ApiConstants.lookUp)
    public ResponseEntity<List<BankLookUpDTO>> lookUp(BaseFilter filter) {
        return ResponseEntity.ok().body(bankService.lookUp(filter));
    }
}
