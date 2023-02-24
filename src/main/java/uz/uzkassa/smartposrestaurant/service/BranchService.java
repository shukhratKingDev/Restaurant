package uz.uzkassa.smartposrestaurant.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.domain.Branch;
import uz.uzkassa.smartposrestaurant.domain.address.Address;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.dto.branch.admin.BranchAdminDTO;
import uz.uzkassa.smartposrestaurant.dto.branch.admin.BranchDetailAdminDTO;
import uz.uzkassa.smartposrestaurant.dto.branch.admin.BranchListAdminDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.mappers.UserMapper;
import uz.uzkassa.smartposrestaurant.web.rest.errors.ConflictException;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:43
 */
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BranchService extends BaseService {

    UserMapper userMapper;

    public String create(BranchAdminDTO branchAdminDto) {
        String companyId = Optional.ofNullable(branchAdminDto.getCompanyId()).orElse(getCurrentCompanyId());
        if (branchRepository.existsByCompanyIdAndDeletedIsFalseAndNameIgnoreCase(companyId, branchAdminDto.getName())) {
            throw new ConflictException("Филиал с таким названием " + branchAdminDto.getName() + " уже существует.");
        }
        Branch branch = new Branch();
        branch.setName(branchAdminDto.getName());
        branch.setAddress(addressMapper.toEntity(branchAdminDto.getAddress()));
        branch.setOwnerId(branchAdminDto.getOwnerId());
        branch.setCompanyId(companyId);
        branchRepository.save(branch);
        return branch.getId();
    }

    @Transactional(readOnly = true)
    public Page<BranchListAdminDTO> getList(BaseFilter filter) {
        ResultList<Branch> resultList = branchRepository.getResultList(filter);
        List<BranchListAdminDTO> result = resultList
            .getList()
            .stream()
            .map(branch -> {
                BranchListAdminDTO branchListAdminDTO = new BranchListAdminDTO();
                branchListAdminDTO.setId(branch.getId());
                branchListAdminDTO.setName(branch.getName());
                branchListAdminDTO.setStatus(branch.getStatus());
                branchListAdminDTO.setAddress(addressMapper.toDto(branch.getAddress()));
                branchListAdminDTO.setOwner(userMapper.toOwnerDto(branch.getOwner()));
                if (isFromAdmin()) {
                    branchListAdminDTO.setCompany(branch.getCompany().toCompanyBaseDTO());
                }
                return branchListAdminDTO;
            })
            .collect(Collectors.toList());
        return new PageImpl<>(result, filter.getSortedPageable(), resultList.getCount());
    }

    @Transactional(readOnly = true)
    public BranchDetailAdminDTO get(String id) {
        return branchRepository
            .findById(id)
            .map(branch -> {
                BranchDetailAdminDTO branchDetailAdminDto = new BranchDetailAdminDTO();
                branchDetailAdminDto.setId(branch.getId());
                branchDetailAdminDto.setName(branch.getName());
                branchDetailAdminDto.setOwner(userMapper.toOwnerDto(branch.getOwner()));
                branchDetailAdminDto.setAddress(addressMapper.toDto(branch.getAddress()));
                if (isFromAdmin()) {
                    branchDetailAdminDto.setCompany(branch.getCompany().toCommonDTO());
                }
                return branchDetailAdminDto;
            })
            .orElseThrow(notFoundExceptionThrow(Branch.class.getSimpleName(), "id", id));
    }

    public String update(String id, BranchAdminDTO branchAdminDTO) {
        return branchRepository
            .findById(id)
            .map(branch -> {
                if (
                    branchRepository.existsByCompanyIdAndDeletedIsFalseAndNameIgnoreCaseAndIdNot(
                        branch.getCompanyId(),
                        branch.getName(),
                        branch.getId()
                    )
                ) {
                    throw new ConflictException("Филиал с таким названием " + branchAdminDTO.getName() + " уже существует.");
                }
                branch.setName(branchAdminDTO.getName());
                if (branchAdminDTO.getOwnerId() != null) {
                    branch.setOwnerId(branchAdminDTO.getOwnerId());
                }
                if (branchAdminDTO.getAddress() != null) {
                    branch.setAddress(
                        addressMapper.merge(branchAdminDTO.getAddress(), Optional.ofNullable(branch.getAddress()).orElse(new Address()))
                    );
                }
                branchRepository.save(branch);
                return branch.getId();
            })
            .orElseThrow(notFoundExceptionThrow(Branch.class.getSimpleName(), "id", id));
    }

    @Transactional(readOnly = true)
    public List<CommonDTO> lookUp(BaseFilter filter) {
        return branchRepository.getResultList(filter).getList().stream().map(Branch::toCommonDTO).collect(Collectors.toList());
    }

    public void updateStatus(String id, CommonStatus status) {
        branchRepository
            .findById(id)
            .map(branch -> {
                branch.setStatus(status);
                branchRepository.save(branch);
                return branch.getId();
            })
            .orElseThrow(notFoundExceptionThrow(Branch.class.getSimpleName(), "id", id));
    }
}
