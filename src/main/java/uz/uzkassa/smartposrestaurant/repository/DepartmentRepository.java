package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.Department;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:02
 */
public interface DepartmentRepository extends BaseRepository<Department, String>, DepartmentRepositoryCustom {
    boolean existsByBranchIdAndDeletedIsFalseAndNameIgnoreCase(String id, String name);

    boolean existsByBranchIdAndDeletedIsFalseAndNameIgnoreCaseAndIdNot(String branchId, String name, String id);
}
