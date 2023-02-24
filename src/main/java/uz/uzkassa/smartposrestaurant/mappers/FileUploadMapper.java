package uz.uzkassa.smartposrestaurant.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uz.uzkassa.smartposrestaurant.domain.FileUpload;
import uz.uzkassa.smartposrestaurant.dto.file.FileDTO;

/**
 * User: Shuxratjon Rayimjonov
 * Date: 11.10.2022 18:39
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface FileUploadMapper {
    FileDTO toLogoDTO(FileUpload entity);
}
