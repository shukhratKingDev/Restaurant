package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.Branch;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 13:54
 */
public interface BranchRepository extends BaseRepository<Branch, String>, BranchRepositoryCustom {
    boolean existsByCompanyIdAndDeletedIsFalseAndNameIgnoreCase(String companyId, String name);

    boolean existsByCompanyIdAndDeletedIsFalseAndNameIgnoreCaseAndIdNot(String companyId, String name, String id);
}
