package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.dto.UnitDTO;
import uz.uzkassa.smartposrestaurant.repository.UnitRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 18.10.2022 17:12
 */
@Transactional
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UnitService extends BaseService {

    UnitRepository unitRepository;

    public List<UnitDTO> getItems() {
        return unitRepository
            .findAllByDeletedIsFalse()
            .stream()
            .map(unit -> new UnitDTO(unit.getId(), unit.getName(), unit.getCode(), unit.getMeasureId()))
            .collect(Collectors.toList());
    }
}
