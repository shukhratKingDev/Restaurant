package uz.uzkassa.smartposrestaurant.repository;

import java.util.List;
import java.util.Optional;
import uz.uzkassa.smartposrestaurant.domain.ActivityType;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:00
 */
public interface ActivityTypeRepository extends BaseRepository<ActivityType, String> {
    Optional<ActivityType> findFirstByCtoIdAndDeletedIsFalse(Long ctoId);

    List<ActivityType> findAllByParentIdAndDeletedIsFalse(String parentId);

    List<ActivityType> findAllByParentIdIsNullAndDeletedIsFalse();
}
