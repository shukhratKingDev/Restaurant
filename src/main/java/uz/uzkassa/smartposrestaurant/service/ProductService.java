package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.constants.Constants;
import uz.uzkassa.smartposrestaurant.domain.Category;
import uz.uzkassa.smartposrestaurant.domain.Product;
import uz.uzkassa.smartposrestaurant.dto.product.ProductDTO;
import uz.uzkassa.smartposrestaurant.repository.CategoryRepository;
import uz.uzkassa.smartposrestaurant.repository.ProductRepository;
import uz.uzkassa.smartposrestaurant.web.rest.errors.BadRequestException;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 15:58
 */
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Slf4j
public class ProductService extends BaseService implements Constants {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    public String create(ProductDTO productDTO) {
        Category category = categoryRepository
            .findById(productDTO.getCategoryId())
            .orElseThrow(notFoundExceptionThrow(Category.class.getSimpleName(), "id", productDTO.getCategoryId()));
        if (!category.getBranchId().equals(productDTO.getBranchId())) {
            throw new BadRequestException(localizationService.localize("wrong.category.branch"));
        }

        Product product = productRepository
            .findFirstByVatBarcodeAndBranchIdAndDeletedIsFalse(productDTO.getVatBarCode(), productDTO.getBranchId())
            .orElse(null);
        if (product != null) {
        }
        return null;
    }
}
