package uz.uzkassa.smartposrestaurant.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import uz.uzkassa.smartposrestaurant.domain.address.Address;
import uz.uzkassa.smartposrestaurant.dto.address.AddressDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.address.AddressDTO;

/**
 * User: Shuxratjon Rayimjonov
 * Date: 11.10.2022 18:22
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressDetailDTO toDto(Address entity);

    Address toEntity(AddressDTO dto);

    Address merge(AddressDTO dto, @MappingTarget Address entity);
}
