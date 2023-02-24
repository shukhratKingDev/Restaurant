package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.uzkassa.smartposrestaurant.domain.permission.UserPermission;

import java.util.List;
import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 11:04
 */
public interface UserPermissionRepository extends BaseRepository<UserPermission, String> {

    @Modifying
    void deleteAllByUserId(String userId);

    @Modifying
    void deleteAllByPermissionCode(String permissionCode);

    Optional<UserPermission> findFirstByUserIdAndPermissionCode(String userId, String permissionCode);

    @Modifying
    @Query("update UserPermission set hasAccess=:hasAccess where userId=:userId and permissionCode not in(:permissionCodes)")
    void updateAccess(
        @Param("userId") String userId,
        @Param("permissionCodes") List<String> permissions,
        @Param("hasAccess") boolean hasAccess
    );

    @Query(value = "SELECT p.permissionCode from UserPermission  p WHERE p.userId=:userId and p.hasAccess=true")
    List<String> findAllPermissionCodesByUserId(@Param("userId") String userId);

    Optional<UserPermission> findByUserIdAndPermissionCode(String userId, String permissionCode);

}
