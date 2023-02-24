package uz.uzkassa.smartposrestaurant.web.rest.cabinet;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyDTO;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.file.FileDTO;
import uz.uzkassa.smartposrestaurant.service.CompanyService;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.25.2022 17:39
 */
@RestController
@RequestMapping(ApiConstants.cabinetCompanyRootApi)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class CabinetCompanyResource {

    CompanyService companyService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.create(companyDTO));
    }

    @GetMapping
    public ResponseEntity<CompanyDetailDTO> get() {
        return ResponseEntity.ok(companyService.get(SecurityUtils.getCurrentCompanyId()));
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.update(SecurityUtils.getCurrentCompanyId(), companyDTO));
    }

    /*@PostMapping("/upload/logo")
    public ResponseEntity<FileDTO> uploadLogo(@RequestParam(required = false) String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(companyService.uploadLogo(Optional.ofNullable(id).orElse(SecurityUtils.getCurrentCompanyId()), file));
    }*/
}
