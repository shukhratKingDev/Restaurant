package uz.uzkassa.smartposrestaurant.repository;

import org.springframework.cache.annotation.Cacheable;
import uz.uzkassa.smartposrestaurant.constants.CacheConstants;
import uz.uzkassa.smartposrestaurant.domain.address.District;

import java.util.List;
import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.10.2022 15:12
 */

public interface DistrictRepository extends BaseRepository<District, String> {
    @Cacheable(cacheNames = CacheConstants.DISTRICT_BY_REGION_CODE_CACHE)
    Optional<District> findByRegionCodeAndCode(String regionCode, String code);

    @Cacheable(cacheNames = CacheConstants.DISTRICT_BY_DISTRICT_ID_CACHE)
    Optional<District> findByDistrictId(String districtId);

    @Cacheable(cacheNames = CacheConstants.DISTRICT_LIST_BY_REGION_ID_CACHE)
    List<District> findAllByRegion_Id(String region_id);

    @Cacheable(cacheNames = CacheConstants.DISTRICT_LIST_BY_REGION_ID_CACHE)
    District findOneByRegionCodeAndCode(Long regionCode, Long districtCode);
}
