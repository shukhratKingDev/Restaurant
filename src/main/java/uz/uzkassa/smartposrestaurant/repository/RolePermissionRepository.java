package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.uzkassa.smartposrestaurant.domain.permission.RolePermission;
import uz.uzkassa.smartposrestaurant.enums.Role;

import java.util.List;
import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 20:56
 */

public interface RolePermissionRepository extends BaseRepository<RolePermission, String> {
    @Modifying
    void deleteAllByPermissionCode(String permissionCode);

    boolean existsByRole(Role role);

    @Modifying
    void deleteAllByRole(Role role);

    @Query(value = "SELECT rp.permissionCode FROM RolePermission rp WHERE rp.role=:role")
    List<String> findAllPermissionCodesByRole(@Param("role") Role role);


}
