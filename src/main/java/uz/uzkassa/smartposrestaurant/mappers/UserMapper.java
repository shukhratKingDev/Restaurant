package uz.uzkassa.smartposrestaurant.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.dto.company.user.OwnerDTO;

/**
 * User: Shuxratjon Rayimjonov
 * Date: 11.10.2022 17:04
 */
@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "login" ,target = "phone")
    OwnerDTO toOwnerDto(User entity);
}
