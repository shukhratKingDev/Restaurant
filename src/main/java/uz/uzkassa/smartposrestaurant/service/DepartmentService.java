package uz.uzkassa.smartposrestaurant.service;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.domain.Department;
import uz.uzkassa.smartposrestaurant.dto.base.CommonDTO;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.dto.department.admin.DepartmentAdminDTO;
import uz.uzkassa.smartposrestaurant.dto.department.admin.DepartmentDetailAdminDTO;
import uz.uzkassa.smartposrestaurant.dto.department.admin.DepartmentListAdminDTO;
import uz.uzkassa.smartposrestaurant.enums.CommonStatus;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.repository.DepartmentRepository;
import uz.uzkassa.smartposrestaurant.web.rest.errors.ConflictException;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 13:54
 */
@Service
@Transactional
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public class DepartmentService extends BaseService {

    DepartmentRepository departmentRepository;

    public String create(DepartmentAdminDTO departmentAdminDTO) {
        String branchId = Optional.ofNullable(departmentAdminDTO.getBranchId()).orElse(getCurrentBranchId());
        if (departmentRepository.existsByBranchIdAndDeletedIsFalseAndNameIgnoreCase(branchId, departmentAdminDTO.getName())) {
            throw new ConflictException("Департмент с таким названием " + departmentAdminDTO.getName() + " уже существует.");
        }
        Department department = new Department();
        department.setName(departmentAdminDTO.getName());
        department.setBranchId(branchId);
        departmentRepository.save(department);
        return department.getId();
    }

    @Transactional(readOnly = true)
    public DepartmentDetailAdminDTO get(String id) {
        return departmentRepository
            .findById(id)
            .map(department -> {
                DepartmentDetailAdminDTO departmentDetailAdminDTO = new DepartmentDetailAdminDTO();
                departmentDetailAdminDTO.setId(department.getId());
                departmentDetailAdminDTO.setName(department.getName());
                departmentDetailAdminDTO.setBranch(department.getBranch().toCommonDTO());
                return departmentDetailAdminDTO;
            })
            .orElseThrow(notFoundExceptionThrow(Department.class.getSimpleName(), "id", id));
    }

    public String update(String id, DepartmentAdminDTO departmentAdminDTO) {
        return departmentRepository
            .findById(id)
            .map(department -> {
                if (
                    departmentRepository.existsByBranchIdAndDeletedIsFalseAndNameIgnoreCaseAndIdNot(
                        department.getBranchId(),
                        departmentAdminDTO.getName(),
                        id
                    )
                ) {
                    throw new ConflictException("Департмент с таким названием " + departmentAdminDTO.getName() + " уже существует.");
                }
                department.setName(departmentAdminDTO.getName());
                department.setBranchId(departmentAdminDTO.getBranchId());
                departmentRepository.save(department);
                return department.getId();
            })
            .orElseThrow(notFoundExceptionThrow(Department.class.getSimpleName(), "id", id));
    }

    @Transactional(readOnly = true)
    public Page<DepartmentListAdminDTO> getList(BaseFilter filter) {
        ResultList<Department> resultList = departmentRepository.getResultList(filter);
        List<DepartmentListAdminDTO> result = resultList
            .getList()
            .stream()
            .map(department -> {
                DepartmentListAdminDTO departmentListAdminDTO = new DepartmentListAdminDTO();
                departmentListAdminDTO.setId(department.getId());
                departmentListAdminDTO.setName(department.getName());
                departmentListAdminDTO.setBranch(department.getBranch().toCommonDTO());
                return departmentListAdminDTO;
            })
            .collect(Collectors.toList());
        return new PageImpl<>(result, filter.getSortedPageable(), resultList.getCount());
    }

    public List<CommonDTO> lookUp(BaseFilter filter) {
        return departmentRepository.getResultList(filter).getList().stream().map(Department::toCommonDTO).collect(Collectors.toList());
    }

    public void updateStatus(String id, CommonStatus status) {
        departmentRepository
            .findById(id)
            .map(department -> {
                department.setStatus(status);
                departmentRepository.save(department);
                return department;
            })
            .orElseThrow(notFoundExceptionThrow(Department.class.getSimpleName(), "id", id));
    }
}
