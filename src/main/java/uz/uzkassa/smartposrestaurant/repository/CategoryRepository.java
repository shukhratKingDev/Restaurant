package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.Category;

import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 16: 19
 */
public interface CategoryRepository extends BaseRepository<Category, String>, CategoryRepositoryCustom {
    Optional<Category> findFirstByCtoId(Long ctoId);

    Optional<Category> findFirstByNameAndBranchIdAndDeletedIsFalse(String name, String branchId);
}
