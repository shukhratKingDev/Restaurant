package uz.uzkassa.smartposrestaurant.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uz.uzkassa.smartposrestaurant.domain.permission.Permission;
import uz.uzkassa.smartposrestaurant.dto.permission.PermissionDTO;

/**
 * User: Shuxratjon Rayimjonov
 * Date: 24.10.2022 19:58
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {

    Permission toEntity(PermissionDTO dto);

    PermissionDTO toDTO(Permission permission);
}
