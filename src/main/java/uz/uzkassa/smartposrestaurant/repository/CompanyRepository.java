package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.company.Company;

import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 13:52
 */

public interface CompanyRepository extends BaseRepository<Company, String>, CompanyRepositoryCustom {
    boolean existsCompanyByTinAndIdNot(String tin, String id);

    Optional<Company> findByTin(String tin);
}
