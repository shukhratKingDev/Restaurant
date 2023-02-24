package uz.uzkassa.smartposrestaurant.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import uz.uzkassa.smartposrestaurant.constants.CacheConstants;
import uz.uzkassa.smartposrestaurant.domain.Unit;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 17.10.2022 13:46
 */
public interface UnitRepository extends BaseRepository<Unit, String> {
    List<Unit> findAllByDeletedIsFalse();

    @Cacheable(cacheNames = CacheConstants.UNIT_BY_CODE_CACHE)
    Unit findFirstByCode(String pc);
}
