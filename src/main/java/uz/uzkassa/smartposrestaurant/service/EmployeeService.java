package uz.uzkassa.smartposrestaurant.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.smartposrestaurant.constants.CacheConstants;
import uz.uzkassa.smartposrestaurant.domain.auth.Authority;
import uz.uzkassa.smartposrestaurant.domain.auth.User;
import uz.uzkassa.smartposrestaurant.domain.company.Company;
import uz.uzkassa.smartposrestaurant.dto.auth.ChangePasswordDTO;
import uz.uzkassa.smartposrestaurant.dto.base.ResultList;
import uz.uzkassa.smartposrestaurant.dto.company.user.OwnerDTO;
import uz.uzkassa.smartposrestaurant.dto.employee.EmployeeDTO;
import uz.uzkassa.smartposrestaurant.dto.employee.EmployeeDetailDTO;
import uz.uzkassa.smartposrestaurant.dto.employee.EmployeeListDTO;
import uz.uzkassa.smartposrestaurant.dto.payload.UserRolePermissionPayload;
import uz.uzkassa.smartposrestaurant.enums.Role;
import uz.uzkassa.smartposrestaurant.enums.UserStatus;
import uz.uzkassa.smartposrestaurant.filters.UserFilter;
import uz.uzkassa.smartposrestaurant.mappers.UserMapper;
import uz.uzkassa.smartposrestaurant.rabbitmq.producer.RabbitMqPermissionProducer;
import uz.uzkassa.smartposrestaurant.repository.AuthorityRepository;
import uz.uzkassa.smartposrestaurant.repository.RolePermissionRepository;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;
import uz.uzkassa.smartposrestaurant.web.rest.errors.BadRequestException;
import uz.uzkassa.smartposrestaurant.web.rest.errors.EntityAlreadyExistException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 14:52
 */
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EmployeeService extends BaseService {

    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    AuthorityRepository authorityRepository;
    RolePermissionRepository rolePermissionRepository;
    PermissionService permissionService;
    RabbitMqPermissionProducer rabbitMqPermissionProducer;


    public OwnerDTO getOwner() {
        Company company = getCurrentCompany();
        OwnerDTO ownerDTO = new OwnerDTO();
        if (company.getOwner() == null) {
            return ownerDTO;
        }
        ownerDTO.setId(company.getOwnerId());
        ownerDTO.setFirstName(company.getOwner().getFirstName());
        ownerDTO.setLastName(company.getOwner().getLastName());
        ownerDTO.setPatronymic(company.getOwner().getPatronymic());
        ownerDTO.setPhone(company.getOwner().getPhone());
        return ownerDTO;
    }

    public String updateOwner(String id, OwnerDTO ownerDT0) {
        Company company = getCurrentCompany();
        User owner = userRepository.findById(id).orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "id", id));

        if (!owner.getId().equals(company.getOwnerId())) {
            throw new BadRequestException(localizationService.localize("owner.does.not.match", new Object[]{owner.getName()}));
        }
        if (ownerDT0.getFirstName() != null) {
            owner.setFirstName(ownerDT0.getFirstName());
        }
        if (ownerDT0.getLastName() != null) {
            owner.setLastName(ownerDT0.getLastName());
        }
        if (ownerDT0.getPatronymic() != null) {
            owner.setPatronymic(ownerDT0.getPatronymic());
        }
        owner = userRepository.save(owner);
        clearCache(owner);
        return owner.getId();
    }

    private void clearCache(User employee) {
        evictCache(CacheConstants.USERS_BY_LOGIN_CACHE, employee.getLogin());
    }

    public String create(EmployeeDTO employeeDTO) {
        User employee = null;
        if (StringUtils.isNotEmpty(employeeDTO.getLogin())) {
            employee = userRepository.findOneByLoginAndDeletedIsFalse(employeeDTO.getLogin()).orElse(null);
        }
        if (StringUtils.isNotBlank(employeeDTO.getTin())) {
            employee = userRepository.findByTinAndDeletedIsFalse(employeeDTO.getTin()).orElse(null);
        }
        if (StringUtils.isNotBlank(employeeDTO.getPinfl())) {
            employee = userRepository.findByPinflAndDeletedIsFalse(employeeDTO.getPinfl()).orElse(null);
        }
        if (employee == null) {
            employee = new User();
        } else if (!Role.CONFIDANT.getCode().equals(employeeDTO.getRole())) {
            throw new EntityAlreadyExistException("Пользователь уже существует");
        }
        employee.setTin(employeeDTO.getTin());
        employee.setPinfl(employeeDTO.getPinfl());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setPatronymic(employeeDTO.getPatronymic());
        employee.setLogin(Optional.ofNullable(employeeDTO.getLogin()).orElse("99800" + RandomStringUtils.randomNumeric(7)).toLowerCase());
        employee.setPassword(
            passwordEncoder.encode(Optional.ofNullable(employeeDTO.getPassword()).orElse(SecurityUtils.generateActivationKey()))
        );
        employee.getAuthorities().add(authorityRepository.findByName(employeeDTO.getRole()).orElseThrow(notFoundExceptionThrow(Role.class.getSimpleName(), "name", employeeDTO.getRole())));
        employee.setActivationKey(SecurityUtils.generateActivationKey());
        employee.setCompanyId(Optional.ofNullable(employeeDTO.getCompanyId()).orElse(getCurrentCompanyId()));
        employee.setStatus(UserStatus.ACTIVE);
        employee = userRepository.save(employee);

        employee.setBranchId(employeeDTO.getBranch());

        rabbitMqPermissionProducer.save(
            new UserRolePermissionPayload(
                employee.getId(), employeeDTO.getRole(),
                rolePermissionRepository.findAllPermissionCodesByRole(Role.getByCode(employeeDTO.getRole()))
            )
        );
        clearCache(employee);
        return employee.getId();
    }

    @Transactional(readOnly = true)
    public EmployeeDetailDTO get(String id) {

        User employee = userRepository.findById(id).orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "id", id));
        EmployeeDetailDTO employeeDetailDTO = new EmployeeDetailDTO();
        employeeDetailDTO.setId(id);
        employeeDetailDTO.setFirstName(employee.getFirstName());
        employeeDetailDTO.setLastName(employee.getLastName());
        employeeDetailDTO.setPatronymic(employee.getPatronymic());
        employeeDetailDTO.setTin(employee.getTin());
        employeeDetailDTO.setPinfl(employee.getPinfl());
        employeeDetailDTO.setLogin(employee.getLogin());
        employeeDetailDTO.setStatus(employee.getStatus());
        employeeDetailDTO.setBranches(employee.getBranch().toCommonDTO());
        if (isFromAdmin()) {
            employeeDetailDTO.setCompany(employee.getCompany().toCommonDTO());
        }
        employee
            .getAuthorities()
            .stream()
            .findFirst()
            .ifPresent(authority -> employeeDetailDTO.setRole(Role.getByCode(authority.getName())));
        return employeeDetailDTO;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeListDTO> getList(UserFilter filter) {
        ResultList<User> resultList = userRepository.getResultList(filter);
        List<EmployeeListDTO> result = resultList
            .getList()
            .stream()
            .map(employee -> {
                EmployeeListDTO employeeListDTO = new EmployeeListDTO();
                employeeListDTO.setId(employee.getId());
                employeeListDTO.setTin(employee.getTin());
                employeeListDTO.setPinfl(employee.getPinfl());
                employeeListDTO.setFirstName(employee.getFirstName());
                employeeListDTO.setLastName(employee.getLastName());
                employeeListDTO.setPatronymic(employee.getPatronymic());
                employeeListDTO.setLogin(employee.getLogin());
                employeeListDTO.setStatus(employee.getStatus());
                employeeListDTO.setBranch(employee.getBranch().toCommonDTO());
                if (isFromAdmin()) {
                    if (employee.getCompany() != null) {
                        employeeListDTO.setCompany(employee.getCompany().toCommonDTO());
                    }
                }
                employee
                    .getAuthorities()
                    .stream()
                    .findFirst()
                    .ifPresent(authority -> employeeListDTO.setRole(Role.getByCode(authority.getName())));
                return employeeListDTO;
            }).collect(Collectors.toList());
        return new PageImpl<>(result, filter.getSortedPageable(), resultList.getCount());
    }

    @Transactional(readOnly = true)
    public List<OwnerDTO> lookUp(UserFilter filter) {
        return userRepository.getResultList(filter).getList().stream().map(userMapper::toOwnerDto).collect(Collectors.toList());
    }

    public String update(String id, EmployeeDTO employeeDTO) {
        User employee = userRepository.findById(id).orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "id", id));
        if (StringUtils.isNotEmpty(employeeDTO.getLogin()) && !employeeDTO.getLogin().equals(employee.getLogin())) {
            if (userRepository.existsUserByLoginAndIdNot(employeeDTO.getLogin(), employee.getId())) {
                throw new EntityAlreadyExistException(
                    localizationService.localize(
                        "entity.already.exist.by.param",
                        new Object[]{
                            localizationService.localize(User.class.getSimpleName()),
                            localizationService.localize("login"),
                            employeeDTO.getLogin(),
                        }
                    ),
                    ""
                );
            }
            employee.setLogin(employeeDTO.getLogin());
        }
        if (StringUtils.isNotEmpty(employeeDTO.getTin()) && !employeeDTO.getTin().equals(employee.getTin())) {
            if (userRepository.existsUserByTinAndIdNot(employeeDTO.getTin(), employee.getId())) {
                throw new EntityAlreadyExistException(
                    localizationService.localize(
                        "entity.already.exist.by.param",
                        new Object[]{
                            localizationService.localize(User.class.getSimpleName()),
                            localizationService.localize("inn"),
                            employeeDTO.getTin(),
                        }
                    ),
                    ""
                );
            }
            employee.setTin(employeeDTO.getTin());
        }
        if (StringUtils.isNotEmpty(employeeDTO.getPinfl()) && !employeeDTO.getPinfl().equals(employee.getPinfl())) {
            if (userRepository.existsUserByPinflAndIdNot(employeeDTO.getPinfl(), employee.getId())) {
                throw new EntityAlreadyExistException(
                    localizationService.localize(
                        "entity.already.exist.by.param",
                        new Object[]{
                            localizationService.localize(User.class.getSimpleName()),
                            localizationService.localize("pinfl"),
                            employeeDTO.getPinfl(),
                        }
                    ),
                    ""
                );
            }
            employee.setPinfl(employeeDTO.getPinfl());
        }
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setPatronymic(employeeDTO.getPatronymic());

        employee.setBranchId(employeeDTO.getBranch());

        if (StringUtils.isNotBlank(employeeDTO.getRole())
            && !employee.getAuthorities().contains(authorityRepository.findById(employeeDTO.getRole()).orElseThrow(notFoundExceptionThrow(Authority.class.getSimpleName(), "id", employeeDTO.getRole())))
        ) {
            employee.getAuthorities().add(authorityRepository.findById(employeeDTO.getRole()).orElseThrow(notFoundExceptionThrow(Authority.class.getSimpleName(), "id", employeeDTO.getRole())));
            permissionService.deleteUserPermissionsByUserId(employee.getId());
            rabbitMqPermissionProducer.save(
                new UserRolePermissionPayload(
                    employee.getId(), employeeDTO.getRole(),
                    rolePermissionRepository.findAllPermissionCodesByRole(Role.getByCode(employeeDTO.getRole()))
                )
            );
        }
        if (employeeDTO.getPassword() != null) {
            employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        }
        userRepository.save(employee);
        clearCache(employee);
        return employee.getId();
    }

    public void updateStatus(String id, UserStatus status) {
        userRepository.findById(id).map(employee -> {
            employee.setStatus(status);
            userRepository.save(employee);
            clearCache(employee);
            return employee;
        }).orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "id", id));
    }

    public void changePassword(String id, ChangePasswordDTO changePasswordDTO) {
        userRepository.findById(id).map(user -> {
            if (!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
                throw new BadRequestException(
                    localizationService.localize("item.invalid", new Object[]{localizationService.localize("password")})
                );
            }
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
            clearCache(user);
            return user;
        }).orElseThrow(notFoundExceptionThrow(User.class.getSimpleName(), "id", id));
    }
}
