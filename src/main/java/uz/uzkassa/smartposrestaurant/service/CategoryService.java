package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import uz.uzkassa.smartposrestaurant.domain.Branch;
import uz.uzkassa.smartposrestaurant.domain.Category;
import uz.uzkassa.smartposrestaurant.domain.Product;
import uz.uzkassa.smartposrestaurant.domain.company.Company;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryDTO;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryListDTO;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryTreeDTO;
import uz.uzkassa.smartposrestaurant.dto.payload.CreateCatalogFromTasnifCatalogPayload;
import uz.uzkassa.smartposrestaurant.dto.soliq.MxikDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.integration.FacturaClient;
import uz.uzkassa.smartposrestaurant.mappers.CategoryMapper;
import uz.uzkassa.smartposrestaurant.rabbitmq.producer.RabbitMqTasnifCatalogProducer;
import uz.uzkassa.smartposrestaurant.repository.CategoryRepository;
import uz.uzkassa.smartposrestaurant.repository.ProductRepository;
import uz.uzkassa.smartposrestaurant.web.rest.errors.BadRequestException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 24.10.2022
 */
@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryService extends BaseService {

    CategoryMapper categoryMapper;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    RabbitMqTasnifCatalogProducer rabbitMqTasnifCatalogProducer;
    FacturaClient facturaClient;

    public String create(CategoryDTO categoryDTO) {
        Category category = categoryRepository.save(categoryMapper.toEntity(categoryDTO));
        return category.getId();
    }

    @Transactional(readOnly = true)
    public CategoryDetailDTO get(String id) {
        return categoryRepository.findById(id).map(category -> {
            CategoryDetailDTO categoryDetailDTO = new CategoryDetailDTO();
            categoryDetailDTO.setId(category.getId());
            categoryDetailDTO.setName(category.getName());
            categoryDetailDTO.setBranch(category.getBranch().toCommonDTO());
            if (category.getParent() != null) {
                categoryDetailDTO.setParent(category.getParent().toCommonDTO());
            }
            return categoryDetailDTO;
        }).orElseThrow(notFoundExceptionThrow(Category.class.getSimpleName(), "id", id));
    }

    @Transactional(readOnly = true)
    public Page<CategoryListDTO> getList(BaseFilter filter) {
        ResultList<Category> resultList = categoryRepository.getResultList(filter);
        List<CategoryListDTO> result =
            resultList.getList().stream().map(category -> {
                CategoryListDTO categoryListDTO = new CategoryListDTO();
                categoryListDTO.setId(category.getId());
                categoryListDTO.setName(category.getName());
                categoryListDTO.setBranch(category.getBranch().toCommonDTO());
                if (category.getParent() != null) {
                    categoryListDTO.setParent(category.getParent().toCommonDTO());
                }
                return categoryListDTO;
            }).collect(Collectors.toList());
        return new PageImpl<>(result, filter.getSortedPageable(), resultList.getCount());
    }

    public String update(String id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(categoryDTO.getName());
            category.setBranchId(categoryDTO.getBranchId());
            category.setParentId(categoryDTO.getParentId());
            categoryRepository.save(category);
            return id;
        }).orElseThrow(notFoundExceptionThrow(Category.class.getSimpleName(), "id", id));
    }


    public void updateStatus(String id, CommonStatus status) {
        categoryRepository.findById(id).map(category -> {
            category.setStatus(status);
            categoryRepository.save(category);
            return category;
        }).orElseThrow(notFoundExceptionThrow(Category.class.getSimpleName(), "id", id));
    }

    @Transactional(readOnly = true)
    public List<CommonDTO> lookUp(BaseFilter filter) {
        return categoryRepository.getResultList(filter)
            .getList()
            .stream()
            .map(Category::toCommonDTO)
            .collect(Collectors.toList());
    }

    public void delete(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(notFoundExceptionThrow(Category.class.getSimpleName(), "id", id));
        if (productRepository.existsByCategoryIdAndDeletedIsFalse(id)) {
            throw new BadRequestException(localizationService.localize("you.can.not.delete.category"));
        }
        if (!CollectionUtils.isEmpty(category.getChildren())) {
            throw new BadRequestException(localizationService.localize("you.can.not.delete.category"));
        }
        categoryRepository.deleteById(id);
    }

    public void sync(String branchId) {
        branchRepository
            .findById(branchId).map(branch -> {
                Company company = branch.getCompany();
                rabbitMqTasnifCatalogProducer.createCatalogFromTasnifCatalogQueue(
                    new CreateCatalogFromTasnifCatalogPayload(company.getId(), company.getTin(), branchId, getCurrentUserId(), getUnit().getId())
                );
                return branch;
            }).orElseThrow(notFoundExceptionThrow(Branch.class.getSimpleName(), "branchId", branchId));
    }

    @Transactional(readOnly = true)
    public List<CategoryTreeDTO> treeList(BaseFilter filter) {

        List<CategoryTreeDTO> categories = categoryRepository.treeList(filter);

        Map<String, CategoryTreeDTO> map = new LinkedHashMap<>();
        List<CategoryTreeDTO> result = new ArrayList<>();

        for (CategoryTreeDTO category : categories) {
            map.put(category.getId(), category);
        }
        categories.forEach(category -> {
            CategoryTreeDTO parentCategory = map.get(category.getParentId());
            if (parentCategory == null) {
                result.add(category);
                return;
            }
            parentCategory.getChildren().add(category);
        });
        result.forEach(category -> sumProductCount(category, category.getChildren()));
        return result;
    }

    private Long sumProductCount(CategoryTreeDTO category, List<CategoryTreeDTO> children) {
        Long sum = category.getProductCount();
        for (CategoryTreeDTO child : children) {
            sum = sum + sumProductCount(child, child.getChildren());
        }
        category.setProductCount(sum);
        return sum;
    }

    public void createCatalogFromTasnifCatalogQueue(CreateCatalogFromTasnifCatalogPayload payload) {
        List<MxikDTO> mxikDTOList = facturaClient.getCompanyCatalog(payload.getTin());
        for (MxikDTO mxikDTO : mxikDTOList) {
            if (
                StringUtils.isBlank(mxikDTO.getGroupName()) ||
                    StringUtils.isBlank(mxikDTO.getClassName()) ||
                    StringUtils.isBlank(mxikDTO.getPositionName())
            ) {
                continue;
            }
            Category groupCategory = categoryRepository
                .findFirstByNameAndBranchIdAndDeletedIsFalse(mxikDTO.getGroupName().trim(), payload.getBranchId())
                .orElse(null);
            if (groupCategory == null) {
                groupCategory = new Category();
                groupCategory.setName(mxikDTO.getGroupName().trim());
                groupCategory.setBranchId(payload.getBranchId());
                groupCategory = categoryRepository.save(groupCategory);
                categoryRepository.flush();
            }
            Category classCategory = categoryRepository
                .findFirstByNameAndBranchIdAndDeletedIsFalse(mxikDTO.getClassName().trim(), payload.getBranchId())
                .orElse(null);
            if (classCategory == null) {
                classCategory = new Category();
                classCategory.setName(mxikDTO.getClassName().trim());
                classCategory.setParentId(groupCategory.getId());
                classCategory.setBranchId(payload.getBranchId());
                classCategory = categoryRepository.save(classCategory);
                categoryRepository.flush();
            }
            Category positionCategory = categoryRepository
                .findFirstByNameAndBranchIdAndDeletedIsFalse(mxikDTO.getPositionName(), payload.getBranchId())
                .orElse(null);
            if (positionCategory == null) {
                positionCategory = new Category();
                positionCategory.setName(mxikDTO.getPositionName());
                positionCategory.setCatalogName(mxikDTO.getMxikCode());
                positionCategory.setParentId(classCategory.getId());
                positionCategory.setBranchId(payload.getBranchId());
                positionCategory = categoryRepository.save(positionCategory);
                categoryRepository.flush();
            }
            if (StringUtils.isNotBlank(mxikDTO.getAttributeName())) {
                Product product = productRepository
                    .findFirstByTasnifIdAndBranchIdAndDeletedIsFalse(mxikDTO.getPkey(), payload.getBranchId())
                    .orElse(new Product());

                product.setTasnifId(mxikDTO.getPkey());
                product.setCatalogName(mxikDTO.getName());
                product.setVatBarcode(mxikDTO.getMxikCode());
                if (!CollectionUtils.isEmpty(mxikDTO.getPackageNames())) {
                    product.setPackageName(mxikDTO.getPackageNames().get(0).getName());
                    product.setPackageCode(mxikDTO.getPackageNames().get(0).getCode());
                }
                if (product.getId() == null) {
                    product.setName(mxikDTO.getName());
                    product.setBarcode(mxikDTO.getInternationalCode());
                    product.setBrand(mxikDTO.getBrandName());
                    product.setCategoryId(positionCategory.getId());
                    product.setBranchId(payload.getBranchId());
                    product.setCompanyId(payload.getCompanyId());
                    product.setUnitId(payload.getUnitId());
                    product.setCreatedDate(LocalDateTime.now());
                    product.setCreatorId(payload.getCreatorId());
                } else {
                    product.setUpdatedDate(LocalDateTime.now());
                    product.setUpdaterId(payload.getCreatorId());
                }
                productRepository.save(product);
                productRepository.flush();
            }
        }
    }
}
