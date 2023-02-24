package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.uzkassa.smartposrestaurant.domain.permission.Permission;
import uz.uzkassa.smartposrestaurant.enums.PermissionType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022 19:53
 */
public interface PermissionRepository extends BaseRepository<Permission, String> {

    Optional<Permission> findByCodeAndDeletedFalse(String permissionCode);

    Optional<Permission> findByCodeAndIdNotAndDeletedIsFalse(String code, String id);

    Set<Permission> findByParentIdAndPermissionTypeAndDeletedIsFalseOrderByPositionAsc(String parentId, PermissionType permissionType);

    @Query("SELECT p FROM Permission p WHERE p.permissionType=:permissionType ORDER BY p.parentId desc ")
    List<Permission> getAll(@Param("permissionType") PermissionType permissionType);
}
