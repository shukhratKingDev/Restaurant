package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.domain.Branch;
import uz.uzkassa.smartposrestaurant.domain.address.Address;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.company.Company;
import uz.uzkassa.smartposrestaurant.dto.activityType.ActivityTypeDTO;
import uz.uzkassa.smartposrestaurant.dto.bank.BankDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyBaseDTO;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyDTO;
import uz.uzkassa.smartposrestaurant.dto.company.CompanyDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.company.admin.CompanyListAdminDTO;
import uz.uzkassa.smartposrestaurant.dto.payload.CreateCatalogFromTasnifCatalogPayload;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.filters.CompanyFilter;
import uz.uzkassa.smartposrestaurant.mappers.AddressMapper;
import uz.uzkassa.smartposrestaurant.mappers.FileUploadMapper;
import uz.uzkassa.smartposrestaurant.mappers.UserMapper;
import uz.uzkassa.smartposrestaurant.rabbitmq.producer.RabbitMqTasnifCatalogProducer;
import uz.uzkassa.smartposrestaurant.web.rest.errors.ConflictException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:47
 */
@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CompanyService extends BaseService {

    UserMapper userMapper;
    AddressMapper addressMapper;
    FileUploadMapper fileUploadMapper;
    RabbitMqTasnifCatalogProducer rabbitMqTasnifCatalogProducer;

    public String create(CompanyDTO companyDTO) {
        Company company = companyRepository.findByTin(companyDTO.getTin()).orElse(null);
        if (company != null && company.getOwner() != null) {
            throw new ConflictException(
                localizationService.localize(
                    "entity.already.exist.by.param",
                    new Object[]{
                        localizationService.localize(Company.class.getSimpleName()),
                        localizationService.localize("inn"),
                        company.getOwner().getLogin(),
                    }
                )
            );
        }
        User user = getCurrentUser();
        company = company != null ? company : new Company();
        company.setOwnerId(user.getId());
        company.setTin(companyDTO.getTin());
        company.setName(companyDTO.getName());
        company.setBusinessType(companyDTO.getBusinessType());

        if (companyDTO.getAddress() != null) {
            Address address = company.getAddress() != null ? company.getAddress() : new Address();
            company.setAddress(addressMapper.merge(companyDTO.getAddress(), address));
        }
        if (companyDTO.getActivityType() != null) {
            company.setActivityTypeId(companyDTO.getActivityType().getId());
        }
        companyRepository.save(company);
        user.setCompanyId(company.getId());
        userRepository.save(user);

        Branch branch = new Branch();
        branch.setName("Main branch");
        branch.setCompanyId(company.getId());
        branch.setOwnerId(user.getId());
        branch.setMain(true);
        branchRepository.save(branch);

        rabbitMqTasnifCatalogProducer.createCatalogFromTasnifCatalogQueue(
            new CreateCatalogFromTasnifCatalogPayload(
                company.getId(), company.getTin(), branch.getId(),
                user.getId(), getUnit().getId()
            )
        );
        return company.getId();
    }

    @Transactional(readOnly = true)
    public Page<CompanyListAdminDTO> getList(CompanyFilter filter) {
        ResultList<Company> resultList = companyRepository.getResultList(filter);
        List<CompanyListAdminDTO> result =
            resultList.getList()
                .stream().map(company -> {
                    CompanyListAdminDTO adminCompanyListDTO = new CompanyListAdminDTO();
                    adminCompanyListDTO.setId(company.getId());
                    adminCompanyListDTO.setName(company.getName());
                    adminCompanyListDTO.setTin(company.getTin());
                    adminCompanyListDTO.setRegistrationDate(company.getRegistrationDate());
                    adminCompanyListDTO.setOwner(userMapper.toOwnerDto(company.getOwner()));
                    return adminCompanyListDTO;
                }).collect(Collectors.toList());
        return new PageImpl<>(result, filter.getSortedPageable(), resultList.getCount());
    }

    @Transactional(readOnly = true)
    public CompanyDetailDTO get(String id) {
        return companyRepository.findById(id).map(company -> {
            CompanyDetailDTO companyDetailDto = new CompanyDetailDTO();
            companyDetailDto.setId(company.getId());
            companyDetailDto.setApayId(company.getApayId());
            companyDetailDto.setName(company.getName());
            companyDetailDto.setTin(company.getTin());
            companyDetailDto.setBusinessType(company.getBusinessType());

            if (company.getActivityType() != null) {
                companyDetailDto.setActivityType(
                    new ActivityTypeDTO(
                        company.getActivityType().getId(),
                        company.getActivityType().getName(),
                        company.getActivityType().getParentId()
                    )
                );
            }
            companyDetailDto.setAddress(addressMapper.toDto(company.getAddress()));

            if (company.getCompanyBank() != null) {
                BankDetailDTO bankDetailDto = new BankDetailDTO();
                bankDetailDto.setId(company.getCompanyBank().getId());
                bankDetailDto.setName(company.getCompanyBank().getBank().getName());
                bankDetailDto.setAccountNumber(company.getCompanyBank().getAccountNumber());
                bankDetailDto.setOked(company.getCompanyBank().getOked());
                bankDetailDto.setMfo(company.getCompanyBank().getBank().getCode());
                companyDetailDto.setBank(bankDetailDto);
            }
            if (company.getLogo() != null) {
                companyDetailDto.setLogo(fileUploadMapper.toLogoDTO(company.getLogo()));
            }
            companyDetailDto.setOwner(userMapper.toOwnerDto(company.getOwner()));
            return companyDetailDto;
        }).orElseThrow(notFoundExceptionThrow(Company.class.getSimpleName(), "id", id));
    }

    public String update(String id, CompanyDTO companyDto) {
        return companyRepository.findById(id).map(company -> {
            if (companyDto.getTin() != null) {
                if (companyRepository.existsCompanyByTinAndIdNot(companyDto.getTin(), id)) {
                    throw new ConflictException(
                        localizationService.localize(
                            "entity.already.exist.by.param",
                            new Object[]{
                                localizationService.localize(Company.class.getSimpleName()),
                                localizationService.localize("inn"),
                                companyDto.getTin()
                            }
                        )
                    );
                }
                company.setTin(companyDto.getTin());
            }
            if (companyDto.getAddress() != null) {
                Address address = Optional.ofNullable(company.getAddress()).orElse(new Address());
                company.setAddress(addressMapper.merge(companyDto.getAddress(), address));
            }
            company.setActivityTypeId(companyDto.getActivityType().getId());
            companyRepository.save(company);
            companyDto.setId(id);
            return company.getId();
        }).orElseThrow(notFoundExceptionThrow(Company.class.getSimpleName(), "id", id));
    }

    @Transactional(readOnly = true)
    public List<CompanyBaseDTO> lookUp(CompanyFilter filter) {
        return companyRepository
            .getResultList(filter)
            .getList()
            .stream()
            .map(Company::toCompanyBaseDTO)
            .collect(Collectors.toList());
    }

    public void updateStatus(String id, CommonStatus status) {
        companyRepository.findById(id).map(company -> {
            company.setStatus(status);
            companyRepository.save(company);
            return company;
        }).orElseThrow(notFoundExceptionThrow(Company.class.getSimpleName(), "id", id));
    }
}
