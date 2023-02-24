package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import uz.uzkassa.smartposrestaurant.domain.Category;
import uz.uzkassa.smartposrestaurant.domain.address.District;
import uz.uzkassa.smartposrestaurant.domain.address.Region;
import uz.uzkassa.smartposrestaurant.dto.CheckCompanyDTO;
import uz.uzkassa.smartposrestaurant.dto.CtsCustomerDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.activityType.ActivityTypeDTO;
import uz.uzkassa.smartposrestaurant.dto.address.AddressFullDTO;
import uz.uzkassa.smartposrestaurant.dto.bank.CtsBankDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.cts.CtsMerchantDTO;
import uz.uzkassa.smartposrestaurant.enums.Vat;
import uz.uzkassa.smartposrestaurant.integration.CtsClient;
import uz.uzkassa.smartposrestaurant.repository.BankRepository;
import uz.uzkassa.smartposrestaurant.repository.CategoryRepository;
import uz.uzkassa.smartposrestaurant.repository.DistrictRepository;
import uz.uzkassa.smartposrestaurant.repository.RegionRepository;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;
import uz.uzkassa.smartposrestaurant.web.rest.errors.BadRequestException;
import uz.uzkassa.smartposrestaurant.web.rest.errors.IntegrationException;

import java.util.Optional;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.21.2022 19:09
 */
@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CtsService extends BaseService {

    CtsClient ctsClient;
    RegionRepository regionRepository;
    DistrictRepository districtRepository;
    BankRepository bankRepository;
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CheckCompanyDTO checkCompany(String tin) {

        HttpHeaders headers = SecurityUtils.getHeader();
        headers.setBearerAuth(ctsClient.getToken());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(applicationProperties.getCtsConfig().getCustomerByTinUrl().concat("/").concat(tin));

        ResponseEntity<CtsCustomerDetailDTO> response = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            entity,
            CtsCustomerDetailDTO.class
        );
        CtsCustomerDetailDTO ctsCustomerDetailDTO = Optional
            .ofNullable(response.getBody())
            .orElseThrow(() -> new IntegrationException(localizationService.localize("error.subtitle")));

        if (!ctsCustomerDetailDTO.isFromCts() && !ctsCustomerDetailDTO.isFromNic()) {
            if (tin.length() == 9) {
                throw new BadRequestException(localizationService.localize("invalid.tin"));
            } else {
                throw new BadRequestException(localizationService.localize("invalid.pinfl"));
            }

        }
        CheckCompanyDTO checkCompanyDTO = new CheckCompanyDTO();
        checkCompanyDTO.setId(ctsCustomerDetailDTO.getId());
        checkCompanyDTO.setName(
            ctsCustomerDetailDTO.getName() != null ? ctsCustomerDetailDTO.getName() : ctsCustomerDetailDTO.getCompanyName()
        );
        checkCompanyDTO.setCompanyName(
            ctsCustomerDetailDTO.getCompanyName() != null ? ctsCustomerDetailDTO.getCompanyName() : ctsCustomerDetailDTO.getName()
        );
        checkCompanyDTO.setTin(ctsCustomerDetailDTO.getTin());
        checkCompanyDTO.setPhone(ctsCustomerDetailDTO.getPhone());
        checkCompanyDTO.setBusinessType(ctsCustomerDetailDTO.getBusinessType());
        checkCompanyDTO.setFromCts(ctsCustomerDetailDTO.isFromCts());
        checkCompanyDTO.setEditable(!ctsCustomerDetailDTO.isFromCts());
        checkCompanyDTO.setFromNic(ctsCustomerDetailDTO.isFromNic());
        checkCompanyDTO.setVatRate(ctsCustomerDetailDTO.getVatRate());
        checkCompanyDTO.setOwner(ctsCustomerDetailDTO.getOwner());

        if (ctsCustomerDetailDTO.getVatRate() != null) {
            checkCompanyDTO.setVat(Vat.getByAmount(ctsCustomerDetailDTO.getVatRate()));
        } else {
            checkCompanyDTO.setVat(Vat.WITHOUT_VAT);
        }
        if (ctsCustomerDetailDTO.getAddress() != null) {
            if (ctsCustomerDetailDTO.getAddress().getRegion() != null) {
                AddressFullDTO addressDTO = new AddressFullDTO();
                Region region = regionRepository.findOneByCode(ctsCustomerDetailDTO.getAddress().getRegion().getCode());
                if (region != null) {
                    addressDTO.setRegion(region.toDTO());
                }

                if (ctsCustomerDetailDTO.getAddress().getDistrict() != null) {
                    if (region != null) {
                        District district = districtRepository.findOneByRegionCodeAndCode(
                            region.getCode(),
                            ctsCustomerDetailDTO.getAddress().getDistrict().getCode()
                        );
                        addressDTO.setDistrict(district.toDto());
                    }
                    addressDTO.setStreet(ctsCustomerDetailDTO.getAddress().getStreet());
                    addressDTO.setHouse(ctsCustomerDetailDTO.getAddress().getHouse());
                    addressDTO.setApartment(ctsCustomerDetailDTO.getAddress().getApartment());
                    addressDTO.setLongitude(ctsCustomerDetailDTO.getAddress().getLongitude());
                    addressDTO.setLatitude(ctsCustomerDetailDTO.getAddress().getLatitude());
                }
                checkCompanyDTO.setAddress(addressDTO);
            }
        }
        if (ctsCustomerDetailDTO.getBank() != null) {
            bankRepository
                .findFirstByMfo(ctsCustomerDetailDTO.getBank().getMfo())
                .ifPresent(
                    bank -> {
                        CtsBankDetailDTO bankDTO = new CtsBankDetailDTO();
                        bankDTO.setId(bank.getId());
                        bankDTO.setName(bank.getName());
                        bankDTO.setAccountNumber(ctsCustomerDetailDTO.getBank().getAccountNumber());
                        bankDTO.setMfo(ctsCustomerDetailDTO.getBank().getMfo());
                        bankDTO.setOked(ctsCustomerDetailDTO.getBank().getOked());
                        bankDTO.setTin(ctsCustomerDetailDTO.getBank().getTin());

                        if (bank.getParent() != null) {
                            CtsBankDetailDTO parentBankDTO = new CtsBankDetailDTO();
                            parentBankDTO.setId(bank.getParent().getId());
                            parentBankDTO.setName(bank.getParent().getName());
                            parentBankDTO.setMfo(bank.getParent().getMfo());
                            bankDTO.setParent(parentBankDTO);
                        }
                        checkCompanyDTO.setBank(bankDTO);
                    }
                );
        }
        ctsCustomerDetailDTO
            .getBranchList()
            .forEach(
                ctsBranchDTO -> {
                    CtsMerchantDTO ctsMerchantDTO = new CtsMerchantDTO();
                    ctsMerchantDTO.setId(ctsBranchDTO.getId());
                    ctsMerchantDTO.setName(ctsBranchDTO.getName());
                    ctsMerchantDTO.setPhone(ctsBranchDTO.getPhone());

                    categoryRepository
                        .findFirstByCtoId(Long.valueOf(ctsBranchDTO.getActivityType().getId()))
                        .ifPresent(
                            activityType -> {
                                ActivityTypeDTO activityTypeDTO = new ActivityTypeDTO();
                                activityTypeDTO.setId(activityType.getId());
                                activityTypeDTO.setName(activityType.getName());
                                activityTypeDTO.setCode(activityType.getCatalogCode().toString());

                                Category parentCategory = categoryRepository.findById(activityType.getParentId()).orElse(null);
                                if (parentCategory != null) {
                                    ActivityTypeDTO parentActivityTypeDTO = new ActivityTypeDTO();
                                    parentActivityTypeDTO.setId(parentCategory.getId());
                                    parentActivityTypeDTO.setName(parentCategory.getName());
                                    if (parentCategory.getCatalogCode() != null) {
                                        parentActivityTypeDTO.setCode(parentCategory.getCatalogCode().toString());
                                    }

                                    activityTypeDTO.setParent(parentActivityTypeDTO);
                                }

                                ctsMerchantDTO.setActivityType(activityTypeDTO);
                            }
                        );

                    if (ctsBranchDTO.getAddress() != null) {
                        if (ctsBranchDTO.getAddress().getRegion() != null) {
                            AddressFullDTO addressDTO = new AddressFullDTO();
                            Region region = regionRepository.findOneByCode(ctsBranchDTO.getAddress().getRegion().getCode());
                            addressDTO.setRegion(region.toDTO());

                            if (ctsBranchDTO.getAddress().getDistrict() != null) {
                                District district = districtRepository.findOneByRegionCodeAndCode(
                                    region.getCode(),
                                    ctsBranchDTO.getAddress().getDistrict().getCode()
                                );
                                addressDTO.setDistrict(district.toDTO());
                                addressDTO.setStreet(ctsBranchDTO.getAddress().getStreet());
                                addressDTO.setHouse(ctsBranchDTO.getAddress().getHouse());
                                addressDTO.setApartment(ctsBranchDTO.getAddress().getApartment());
                                addressDTO.setLongitude(ctsBranchDTO.getAddress().getLongitude());
                                addressDTO.setLatitude(ctsBranchDTO.getAddress().getLatitude());
                            }
                            ctsMerchantDTO.setAddress(addressDTO);
                        }
                    }
                    checkCompanyDTO.addBranch(ctsMerchantDTO);
                }
            );

        return checkCompanyDTO;
    }

}
