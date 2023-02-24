package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.base.CommonCodeDTO;
import uz.uzkassa.smartposrestaurant.dto.permission.PermissionDTO;
import uz.uzkassa.smartposrestaurant.dto.permission.PermissionListDTO;
import uz.uzkassa.smartposrestaurant.dto.permission.SyncPermissionListDTO;
import uz.uzkassa.smartposrestaurant.dto.permission.UserRolePermissionDTO;
import uz.uzkassa.smartposrestaurant.enums.Role;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.service.PermissionService;

import javax.validation.Valid;
import java.util.List;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 19:43
 */
@RestController
@RequestMapping(ApiConstants.adminPermissionRootApi)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AdminPermissionResource {

    PermissionService permissionService;

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody PermissionDTO permissionDTO) {
        return ResponseEntity.ok(permissionService.create(permissionDTO));
    }

    @PutMapping(ApiConstants.id)
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody PermissionDTO permissionDTO) {
        return ResponseEntity.ok(permissionService.update(id, permissionDTO));
    }

    @GetMapping(ApiConstants.id)
    public ResponseEntity<PermissionDTO> get(@PathVariable String id) {
        return ResponseEntity.ok(permissionService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<PermissionListDTO>> getList(BaseFilter filter) {
        return ResponseEntity.ok().body(permissionService.getList(filter));
    }

    @DeleteMapping(ApiConstants.id)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        permissionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<Void> saveUserPermissions(@PathVariable String userId, @RequestBody UserRolePermissionDTO userRolePermissionDTO) {

        permissionService.saveUserPermissions(userId, userRolePermissionDTO.getPermissions());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<String>> getUserPermissions(@PathVariable("userId") String userId) {
        return ResponseEntity.ok().body(permissionService.getUserPermissions(userId));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<CommonCodeDTO>> getRoles() {
        return ResponseEntity.ok().body(Role.getOrganizationCodesAsCommonDTO());
    }

    @PostMapping("/roles/{role}")
    public ResponseEntity<Void> saveRolePermissions(
        @PathVariable("role") String role,
        @RequestBody UserRolePermissionDTO userRolePermissionDTO
    ) {
        permissionService.saveRolePermissions(Role.getByCode(role), userRolePermissionDTO.getPermissions());
        return ResponseEntity.ok().build();
    }

    @GetMapping("roles/{role}")
    public ResponseEntity<List<String>> getRolePermissions(@PathVariable("role") String role) {

        return ResponseEntity.ok().body(permissionService.getRolePermissions(Role.getByCode(role)));
    }

    @GetMapping(ApiConstants.sync)
    public ResponseEntity<Void> sync() {
        permissionService.sync();
        return ResponseEntity.ok().build();
    }

    @GetMapping(ApiConstants.all)
    public ResponseEntity<List<SyncPermissionListDTO>> getAllList(BaseFilter filter) {
        return ResponseEntity.ok().body(permissionService.getAllList(filter));
    }
}
