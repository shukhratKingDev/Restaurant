package uz.uzkassa.smartposrestaurant.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uz.uzkassa.smartposrestaurant.domain.Branch;
import uz.uzkassa.smartposrestaurant.dto.branch.admin.BranchDetailAdminDTO;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 17:26
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BranchMapper {
    BranchDetailAdminDTO toDTO(Branch entity);
}
