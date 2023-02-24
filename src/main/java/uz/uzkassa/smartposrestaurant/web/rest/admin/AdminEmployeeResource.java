package uz.uzkassa.smartposrestaurant.web.rest.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzkassa.smartposrestaurant.constants.ApiConstants;
import uz.uzkassa.smartposrestaurant.dto.auth.ChangePasswordDTO;
import uz.uzkassa.smartposrestaurant.dto.company.user.OwnerDTO;
import uz.uzkassa.smartposrestaurant.dto.employee.EmployeeDTO;
import uz.uzkassa.smartposrestaurant.dto.employee.EmployeeDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.employee.EmployeeListDTO;
import uz.uzkassa.smartposrestaurant.enums.Role;
import uz.uzkassa.smartposrestaurant.enums.UserStatus;
import uz.uzkassa.smartposrestaurant.filters.UserFilter;
import uz.uzkassa.smartposrestaurant.service.EmployeeService;

import java.util.EnumSet;
import java.util.List;

import static uz.uzkassa.smartposrestaurant.enums.Role.*;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 13.10.2022 14:25
 */
@RestController
@RequestMapping(ApiConstants.adminEmployeeRootApi)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AdminEmployeeResource {

    EmployeeService employeeService;

    @GetMapping("/owner")
    public ResponseEntity<OwnerDTO> getOwner() {
        return ResponseEntity.ok(employeeService.getOwner());
    }

    @PutMapping("/owner/{id}")
    public ResponseEntity<String> updateOwner(@PathVariable String id, @RequestBody OwnerDTO ownerDT0) {
        return ResponseEntity.ok(employeeService.updateOwner(id, ownerDT0));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.create(employeeDTO));
    }

    @GetMapping(ApiConstants.id)
    public ResponseEntity<EmployeeDetailDTO> get(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeListDTO>> getList(UserFilter filter) {
        return ResponseEntity.ok(employeeService.getList(filter));
    }

    @GetMapping(ApiConstants.lookUp)
    public ResponseEntity<List<OwnerDTO>> lookUp(UserFilter filter) {
        return ResponseEntity.ok(employeeService.lookUp(filter));
    }

    @GetMapping(ApiConstants.statuses)
    public ResponseEntity<EnumSet<UserStatus>> getStatuses() {
        return ResponseEntity.ok().body(EnumSet.allOf(UserStatus.class));
    }

    @GetMapping(ApiConstants.roles)
    public ResponseEntity<EnumSet<Role>> getRoles() {
        return ResponseEntity.ok().body(EnumSet.of(ADMIN, MODERATOR, OPERATOR, SUPPORT));
    }

    @PutMapping(ApiConstants.id)
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.update(id, employeeDTO));
    }

    @PatchMapping(ApiConstants.updateStatus)
    public ResponseEntity<Void> updateStatus(@PathVariable String id, @RequestParam("status") String status) {
        employeeService.updateStatus(id, UserStatus.valueOf(status));
        return ResponseEntity.ok().build();
    }

    @PatchMapping(ApiConstants.id + "/password")
    public ResponseEntity<Void> changePassword(@PathVariable String id, @RequestBody ChangePasswordDTO changePasswordDTO) {
        employeeService.changePassword(id, changePasswordDTO);
        return ResponseEntity.ok().build();
    }

}
