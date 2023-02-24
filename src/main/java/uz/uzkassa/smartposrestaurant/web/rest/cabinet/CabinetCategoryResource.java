package uz.uzkassa.smartposrestaurant.web.rest.cabinet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryTreeDTO;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.service.CategoryService;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.22.2022 14:37
 */
@RestController
@RequestMapping(ApiConstants.cabinetCategoryRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CabinetCategoryResource {
    CategoryService categoryService;

    @GetMapping("/tree")
    public ResponseEntity<List<CategoryTreeDTO>> treeList(BaseFilter filter) {
        if (SecurityUtils.getCurrentCompanyId() == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(categoryService.treeList(filter));
    }

    @PostMapping(ApiConstants.sync + "/{branchId}")
    public ResponseEntity<Void> sync(@PathVariable String branchId) {
        categoryService.sync(branchId);
        return ResponseEntity.ok().build  ();
    }
}
