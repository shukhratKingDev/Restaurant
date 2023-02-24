package uz.uzkassa.smartposrestaurant.repository;

import uz.uzkassa.smartposrestaurant.domain.Product;

import java.util.List;
import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 13:41
 */

public interface ProductRepository extends BaseRepository<Product, String> {
    Optional<Product> findFirstByVatBarcodeAndBranchIdAndDeletedIsFalse(String vatBarcode, String branchId);

    boolean existsByCategoryIdAndDeletedIsFalse(String categoryId);

    List<Product> findAllByCompanyIdAndVatBarcodeIsNotNullAndDeletedIsFalse(String companyId);

    Optional<Product> findFirstByTasnifIdAndBranchIdAndDeletedIsFalse(String tasnifId, String branchId);
}
