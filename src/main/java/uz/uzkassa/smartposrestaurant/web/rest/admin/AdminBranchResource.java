package uz.uzkassa.smartposrestaurant.web.rest.admin;

import java.util.EnumSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.branch.admin.BranchAdminDTO;
import uz.uzkassa.smartposrestaurant.dto.branch.admin.BranchDetailAdminDTO;
import uz.uzkassa.smartposrestaurant.dto.branch.admin.BranchListAdminDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.service.BranchService;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 12.10.2022 11:38
 */
@RestController
@RequestMapping(ApiConstants.adminBranchRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AdminBranchResource {

    BranchService branchService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody BranchAdminDTO branchAdminDto) {
        return ResponseEntity.ok().body(branchService.create(branchAdminDto));
    }

    @GetMapping(ApiConstants.id)
    public ResponseEntity<BranchDetailAdminDTO> get(@PathVariable String id) {
        return ResponseEntity.ok(branchService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<BranchListAdminDTO>> getList(BaseFilter filter) {
        return ResponseEntity.ok(branchService.getList(filter));
    }

    @PutMapping(ApiConstants.id)
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody BranchAdminDTO branchAdminDTO) {
        return ResponseEntity.ok(branchService.update(id, branchAdminDTO));
    }

    @GetMapping(ApiConstants.lookUp)
    public ResponseEntity<List<CommonDTO>> lookUp(BaseFilter filter) {
        filter.setStatus(CommonStatus.ACTIVE.getCode());
        return ResponseEntity.ok(branchService.lookUp(filter));
    }

    @PatchMapping(ApiConstants.updateStatus)
    public ResponseEntity<Void> updateStatus(@PathVariable String id, @RequestParam("status") String status) {
        branchService.updateStatus(id, CommonStatus.valueOf(status));
        return ResponseEntity.ok().build();
    }

    @GetMapping(ApiConstants.statuses)
    public ResponseEntity<EnumSet<CommonStatus>> getStatuses() {
        return ResponseEntity.ok().body(EnumSet.allOf(CommonStatus.class));
    }
}
