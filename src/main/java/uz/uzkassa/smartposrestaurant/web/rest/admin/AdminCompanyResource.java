package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyBaseDTO;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyDTO;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.company.admin.CompanyListAdminDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.filters.CompanyFilter;
import uz.uzkassa.smartposrestaurant.service.CompanyService;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 18:20
 */
@RestController
@RequestMapping(ApiConstants.adminCompanyRootApi)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class AdminCompanyResource {

    CompanyService companyService;

    @GetMapping
    public ResponseEntity<Page<CompanyListAdminDTO>> getList(CompanyFilter filter) {
        return ResponseEntity.ok(companyService.getList(filter));
    }

    @GetMapping(ApiConstants.id)
    public ResponseEntity<CompanyDetailDTO> get(@PathVariable String id) {
        return ResponseEntity.ok(companyService.get(id));
    }

    @PutMapping(ApiConstants.id)
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody CompanyDTO companyDto) {
        return ResponseEntity.ok(companyService.update(id, companyDto));
    }

    @GetMapping(ApiConstants.lookUp)
    public ResponseEntity<List<CompanyBaseDTO>> lookUp(CompanyFilter filter) {
        return ResponseEntity.ok().body(companyService.lookUp(filter));
    }

    @PatchMapping(ApiConstants.updateStatus)
    public ResponseEntity<Void> updateStatus(@PathVariable String id, @RequestParam("status") String status) {
        companyService.updateStatus(id, CommonStatus.valueOf(status));
        return ResponseEntity.ok().build();
    }
}
