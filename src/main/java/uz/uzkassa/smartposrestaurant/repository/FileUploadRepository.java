package uz.uzkassa.smartposrestaurant.repository;

import java.util.List;
import uz.uzkassa.smartposrestaurant.domain.FileUpload;
import uz.uzkassa.smartposrestaurant.enums.EntityTypeEnum;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:01
 */
public interface FileUploadRepository extends BaseRepository<FileUpload, String> {
    List<FileUpload> findAllByEntityIdAndEntityType(String entityId, EntityTypeEnum entityType);
}
