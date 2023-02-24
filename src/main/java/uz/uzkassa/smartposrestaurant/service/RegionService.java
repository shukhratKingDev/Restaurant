package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.domain.address.Region;
import uz.uzkassa.smartposrestaurant.dto.address.RegionDistrictDTO;
import uz.uzkassa.smartposrestaurant.repository.RegionRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:57
 */
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RegionService extends BaseService {

    RegionRepository regionRepository;

    public List<RegionDistrictDTO> getItems() {
        return regionRepository.findAll().stream()
            .map(Region::toDTO).
            collect(Collectors.toList());
    }
}
