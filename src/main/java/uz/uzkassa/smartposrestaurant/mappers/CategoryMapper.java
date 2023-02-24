package uz.uzkassa.smartposrestaurant.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uz.uzkassa.smartposrestaurant.domain.Category;
import uz.uzkassa.smartposrestaurant.dto.category.CategoryDTO;

/**
 * User: Shuxratjon Rayimjono
 * Date:24.10.2022 16:50
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category toEntity(CategoryDTO dto);
}
