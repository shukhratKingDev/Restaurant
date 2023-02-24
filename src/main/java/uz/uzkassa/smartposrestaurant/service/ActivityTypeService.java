package uz.uzkassa.smartposrestaurant.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;
import uz.uzkassa.smartposrestaurant.domain.ActivityType;
import uz.uzkassa.smartposrestaurant.dto.activityType.CtsSyncActivityTypeDTO;
import uz.uzkassa.smartposrestaurant.dto.activityType.SyncCtsActivityTypeListResponseDTO;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.enums.SortTypeEnum;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.integration.CtsClient;
import uz.uzkassa.smartposrestaurant.repository.ActivityTypeRepository;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:32
 */
@Service
@Transactional
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class ActivityTypeService extends BaseService {
    ActivityTypeRepository activityTypeRepository;
    CtsClient ctsClient;

    public void sync() {
        HttpHeaders headers = SecurityUtils.getHeader();
        headers.setBearerAuth(ctsClient.getToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(applicationProperties.getCtsConfig().getActivityTypeListUrl())
            .queryParam("page", 0)
            .queryParam("size", 10000)
            .queryParam("orderBy", "parentId")
            .queryParam("sortOrder", SortTypeEnum.desc.name());

        ResponseEntity<SyncCtsActivityTypeListResponseDTO> response = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            entity,
            SyncCtsActivityTypeListResponseDTO.class
        );

        if (response.getBody() != null && !CollectionUtils.isEmpty(response.getBody().getContent())) {

            for (CtsSyncActivityTypeDTO activityDTO : response.getBody().getContent()) {
                ActivityType activityType = activityTypeRepository.findFirstByCtoIdAndDeletedIsFalse(activityDTO.getId()).orElse(new ActivityType());
                activityType.setCtoId(activityDTO.getId());
                activityType.setNameUz(activityDTO.getNameUz());
                activityType.setNameCyrillic(activityDTO.getNameCyrillic());
                activityType.setNameRu(activityDTO.getNameRu());
                activityType.setCode(activityDTO.getCode());
                activityTypeRepository.save(activityType);

                if (activityDTO.getParentId() != null) {
                    ActivityType parent = activityTypeRepository.findFirstByCtoIdAndDeletedIsFalse(activityDTO.getParentId()).orElse(new ActivityType());
                    parent.setCtoId(activityDTO.getParentId());
                    parent.setCode(activityDTO.getParentCode());
                    activityTypeRepository.save(parent);
                    activityType.setParentId(parent.getParentId());
                }
            }
        }

    }

    @Transactional(readOnly = true)
    public List<CommonDTO> lookUp(BaseFilter filter) {
        List<CommonDTO> result = new ArrayList<>();

        if (filter.getParentId() == null) {
            activityTypeRepository.findAllByParentIdIsNullAndDeletedIsFalse().forEach(activityType -> result.add(activityType.toCommonDto()));
            return result;
        }
        activityTypeRepository.findAllByParentIdAndDeletedIsFalse(filter.getParentId()).forEach(activityType -> result.add(activityType.toCommonDto()));
        return result;
    }
}




