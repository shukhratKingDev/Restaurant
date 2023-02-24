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
import uz.uzkassa.smartposrestaurant.domain.Bank;
import uz.uzkassa.smartposrestaurant.dto.bank.BankLookUpDTO;
import uz.uzkassa.smartposrestaurant.dto.bank.SyncCtoBankListResponseDTO;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.enums.SortTypeEnum;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.integration.CtsClient;
import uz.uzkassa.smartposrestaurant.repository.BankRepository;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 18:39
 */
@Service
@Transactional
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class BankService extends BaseService {

    BankRepository bankRepository;
    CtsClient ctsClient;

    public void sync() {
        HttpHeaders headers = SecurityUtils.getHeader();
        headers.setBearerAuth(ctsClient.getToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(applicationProperties.getCtsConfig().getBankListUrl())
            .queryParam("page", 0)
            .queryParam("size", 10000)
            .queryParam("orderBy", "parent_id")
            .queryParam("sortOrder", SortTypeEnum.desc.getName());

        ResponseEntity<SyncCtoBankListResponseDTO> response = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            entity,
            SyncCtoBankListResponseDTO.class
        );

        if (response.getBody() != null && !CollectionUtils.isEmpty(response.getBody().getContent())) {
            response
                .getBody()
                .getContent()
                .forEach(syncCtoBankListDto -> {
                    Bank bank = bankRepository.findByMfo(syncCtoBankListDto.getCode()).orElse(new Bank());
                    if (syncCtoBankListDto.getName().split(",").length > 1) {
                        bank.setNameUz(syncCtoBankListDto.getName().split(",")[1].trim());
                    } else {
                        bank.setNameUz(syncCtoBankListDto.getName().trim());
                    }
                    bank.setNameRu(bank.getNameUz());

                    if (syncCtoBankListDto.getCategoryCode().length() > 2) {
                        bank.setCode(syncCtoBankListDto.getCategoryCode().substring(1, 3));
                    } else {
                        bank.setCode(syncCtoBankListDto.getCategoryCode());
                    }
                    bank.setMfo(syncCtoBankListDto.getCode());
                    bank.setTin(syncCtoBankListDto.getTin());

                    if (syncCtoBankListDto.getParentCode() != null) {
                        bankRepository.findByMfo(syncCtoBankListDto.getParentCode())
                            .ifPresent(parentBank -> bank.setParentId(parentBank.getId()));
                    }
                    bankRepository.save(bank);
                });
        }
    }

    @Transactional(readOnly = true)
    public List<BankLookUpDTO> lookUp(BaseFilter filter) {
        ResultList<Bank> resultList = bankRepository.getResultList(filter);
        List<BankLookUpDTO> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(resultList.getList()) && filter.getParentId() != null) {
            bankRepository.findById(filter.getParentId()).ifPresent(bank -> result.add(bank.toLookUpDto()));
        }
        resultList.getList().forEach(bank -> result.add(bank.toLookUpDto()));
        return result;
    }

}
