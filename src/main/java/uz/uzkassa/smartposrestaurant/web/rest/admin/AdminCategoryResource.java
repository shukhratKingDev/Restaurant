package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryDTO;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryListDTO;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryTreeDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.service.CategoryService;

import java.util.EnumSet;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 16:27
 */
@RestController
@RequestMapping(ApiConstants.adminCategoryRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AdminCategoryResource {

    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.create(categoryDTO));
    }


    @GetMapping(ApiConstants.id)
    public ResponseEntity<CategoryDetailDTO> get(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryListDTO>> getList(BaseFilter filter) {
        return ResponseEntity.ok(categoryService.getList(filter));
    }

    @PutMapping(ApiConstants.id)
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.update(id, categoryDTO));
    }

    @PatchMapping(ApiConstants.updateStatus)
    public ResponseEntity<Void> updateStatus(@PathVariable String id, @RequestParam("status") String status) {
        categoryService.updateStatus(id, CommonStatus.valueOf(status));
        categoryService.updateStatus(id, CommonStatus.valueOf(status));
        return ResponseEntity.ok().build();
    }

    @GetMapping(ApiConstants.statuses)
    public ResponseEntity<EnumSet<CommonStatus>> getStatus() {
        return ResponseEntity.ok().body(EnumSet.allOf(CommonStatus.class));
    }

    @GetMapping(ApiConstants.lookUp)
    public ResponseEntity<List<CommonDTO>> lookUp(BaseFilter filter) {
        return ResponseEntity.ok(categoryService.lookUp(filter));
    }

    @DeleteMapping(ApiConstants.id)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(ApiConstants.sync + "/{branchId}")
    public ResponseEntity<Void> sync(@PathVariable String branchId) {
        categoryService.sync(branchId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tree")
    public ResponseEntity<List<CategoryTreeDTO>> treeList(BaseFilter filter) {
        return ResponseEntity.ok(categoryService.treeList(filter));
    }

}

