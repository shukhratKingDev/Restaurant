package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.domain.address.District;
import uz.uzkassa.smartposrestaurant.dto.address.RegionDistrictDTO;
import uz.uzkassa.smartposrestaurant.repository.DistrictRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:50
 */
@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DistrictService extends BaseService {

    DistrictRepository districtRepository;

    @Transactional(readOnly = true)
    public List<RegionDistrictDTO> getItemsByRegionId(String regionId) {
        return districtRepository.findAllByRegion_Id(regionId)
            .stream()
            .map(District::toDto)
            .collect(Collectors.toList());
    }
}
