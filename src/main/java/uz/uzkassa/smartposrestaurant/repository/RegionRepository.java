package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.cache.annotation.Cacheable;
import uz.uzkassa.smartposrestaurant.constants.CacheConstants;
import uz.uzkassa.smartposrestaurant.domain.address.Region;

import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 13:58
 */
public interface RegionRepository extends BaseRepository<Region, String> {
    @Cacheable(cacheNames = CacheConstants.REGION_BY_CODE_CACHE)
    Region findOneByCode(Long code);
}
