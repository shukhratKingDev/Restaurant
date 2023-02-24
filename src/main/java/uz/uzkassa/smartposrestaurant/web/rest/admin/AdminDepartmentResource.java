package uz.uzkassa.smartposrestaurant.web.rest.admin;

import static lombok.AccessLevel.PRIVATE;

import java.util.EnumSet;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.department.admin.DepartmentAdminDTO;
import uz.uzkassa.smartposrestaurant.dto.department.admin.DepartmentDetailAdminDTO;
import uz.uzkassa.smartposrestaurant.dto.department.admin.DepartmentListAdminDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.service.DepartmentService;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 13:51
 */
@RestController
@RequestMapping(ApiConstants.adminDepartmentRootApi)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AdminDepartmentResource {

    DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody DepartmentAdminDTO departmentAdminDTO) {
        return ResponseEntity.ok().body(departmentService.create(departmentAdminDTO));
    }

    @GetMapping(ApiConstants.id)
    public ResponseEntity<DepartmentDetailAdminDTO> get(@PathVariable String id) {
        return ResponseEntity.ok(departmentService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<DepartmentListAdminDTO>> getList(BaseFilter filter) {
        return ResponseEntity.ok(departmentService.getList(filter));
    }

    @PutMapping(ApiConstants.id)
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody DepartmentAdminDTO departmentAdminDTO) {
        return ResponseEntity.ok(departmentService.update(id, departmentAdminDTO));
    }

    @GetMapping(ApiConstants.lookUp)
    public ResponseEntity<List<CommonDTO>> lookUp(BaseFilter filter) {
        return ResponseEntity.ok(departmentService.lookUp(filter));
    }

    @PatchMapping(ApiConstants.updateStatus)
    public ResponseEntity<Void> updateStatus(@PathVariable String id, @RequestParam("status") String status) {
        departmentService.updateStatus(id, CommonStatus.valueOf(status));
        return ResponseEntity.ok().build();
    }

    @GetMapping(ApiConstants.statuses)
    public ResponseEntity<EnumSet<CommonStatus>> getStatuses() {
        return ResponseEntity.ok().body(EnumSet.allOf(CommonStatus.class));
    }
}
