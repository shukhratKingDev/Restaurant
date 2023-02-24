package uz.uzkassa.smartposrestaurant.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;
import uz.uzkassa.smartposrestaurant.domain.permission.Permission;
import uz.uzkassa.smartposrestaurant.domain.permission.RolePermission;
import uz.uzkassa.smartposrestaurant.domain.permission.UserPermission;
import uz.uzkassa.smartposrestaurant.dto.payload.UserRolePermissionPayload;
import uz.uzkassa.smartposrestaurant.dto.permission.PermissionDTO;
import uz.uzkassa.smartposrestaurant.dto.permission.PermissionListDTO;
import uz.uzkassa.smartposrestaurant.dto.permission.SyncPermissionListDTO;
import uz.uzkassa.smartposrestaurant.enums.PermissionType;
import uz.uzkassa.smartposrestaurant.enums.Role;
import uz.uzkassa.smartposrestaurant.enums.SortTypeEnum;
import uz.uzkassa.smartposrestaurant.filters.BaseFilter;
import uz.uzkassa.smartposrestaurant.mappers.PermissionMapper;
import uz.uzkassa.smartposrestaurant.rabbitmq.producer.RabbitMqPermissionProducer;
import uz.uzkassa.smartposrestaurant.repository.PermissionRepository;
import uz.uzkassa.smartposrestaurant.repository.RolePermissionRepository;
import uz.uzkassa.smartposrestaurant.repository.UserPermissionRepository;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;
import uz.uzkassa.smartposrestaurant.web.rest.errors.EntityAlreadyExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 14.10.2022 11:01
 */
@Service
@Transactional
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
public class PermissionService extends BaseService {

    UserPermissionRepository userPermissionRepository;

    RabbitMqPermissionProducer rabbitMqPermissionProducer;

    PermissionRepository permissionRepository;

    PermissionMapper permissionMapper;

    RolePermissionRepository rolePermissionRepository;

    public void deleteUserPermissionsByUserId(String userId) {
        userPermissionRepository.deleteAllByUserId(userId);
    }

    public String create(PermissionDTO permissionDTO) {
        if (permissionRepository.findByCodeAndDeletedFalse(permissionDTO.getCode()).isPresent()) {
            throw new EntityAlreadyExistException("Permission has already been existed with this code: " + permissionDTO.getCode());
        }
        Permission permission = permissionMapper.toEntity(permissionDTO);
        permission = permissionRepository.save(permission);
        return permission.getId();
    }

    public String update(String id, PermissionDTO permissionDTO) {
        if (permissionRepository.findByCodeAndIdNotAndDeletedIsFalse(permissionDTO.getCode(), id).isPresent()) {
            throw new EntityAlreadyExistException("Permission has already been existed with this code: " + permissionDTO.getCode());
        }
        return permissionRepository.findById(id)
            .map(permission -> {
                permission.setCode(permissionDTO.getCode());
                permission.setName(permissionDTO.getName());
                permission.setPosition(permission.getPosition());
                permission.setParentId(permission.getParentId());
                permission.setPermissionType(permissionDTO.getPermissionType());
                permission.setSection(permissionDTO.isSection());
                permissionRepository.save(permission);
                return permissionDTO.getId();
            }).orElseThrow(notFoundExceptionThrow(Permission.class.getSimpleName(), "id", permissionDTO.getCode()));
    }


    public PermissionDTO get(String id) {
        return permissionRepository.findById(id)
            .map(permissionMapper::toDTO).orElseThrow(notFoundExceptionThrow(Permission.class.getSimpleName(), "id", id));
    }

    public List<PermissionListDTO> getList(BaseFilter filter) {

        return recursion(permissionRepository.findByParentIdAndPermissionTypeAndDeletedIsFalseOrderByPositionAsc(
            filter.getParentId(),
            PermissionType.valueOf(filter.getType()))
        );
    }

    private List<PermissionListDTO> recursion(Set<Permission> permissions) {

        return permissions.stream()
            .map(permission -> {
                PermissionListDTO permissionListDTO = new PermissionListDTO();

                permissionListDTO.setId(permission.getId());
                permissionListDTO.setName(permission.getName());
                permissionListDTO.setPosition(permission.getPosition());
                permissionListDTO.setSection(permission.isSection());
                permissionListDTO.setParentId(permission.getParentId());
                permissionListDTO.setPermissionType(permission.getPermissionType());
                if (!CollectionUtils.isEmpty(permission.getChildren())) {
                    permissionListDTO.setChildren(recursion(permission.getChildren()));
                }
                return permissionListDTO;
            }).collect(Collectors.toList());
    }

    public void deleteById(String id) {
        Permission permission = permissionRepository
            .findById(id)
            .orElseThrow(notFoundExceptionThrow(Permission.class.getSimpleName(), "id", id));
        userPermissionRepository.deleteAllByPermissionCode(permission.getCode());
        userPermissionRepository.flush();

        rolePermissionRepository.deleteAllByPermissionCode(permission.getCode());
        permissionRepository.delete(permission);
    }

    public void saveUserPermissions(String userId, List<String> permissions) {

        List<UserPermission> userPermissions = new ArrayList<>();
        permissions.forEach(permissionCode -> {
            UserPermission userPermission = userPermissionRepository
                .findFirstByUserIdAndPermissionCode(userId, permissionCode)
                .orElse(new UserPermission());
            if (userPermission.getId() == null) {
                userPermission.setPermissionCode(permissionCode);
                userPermission.setUserId(userId);
            }
            userPermission.setHasAccess(true);
            userPermissions.add(userPermission);
        });
        if (!CollectionUtils.isEmpty(userPermissions)) {
            userPermissionRepository.saveAll(userPermissions);
        }
        userPermissionRepository.updateAccess(userId, permissions, false);
    }

    @Transactional(readOnly = true)
    public List<String> getUserPermissions(String userId) {
        return userPermissionRepository.findAllPermissionCodesByUserId(userId);
    }


    public void saveRolePermissions(Role role, List<String> permissions) {
        boolean existByRole = rolePermissionRepository.existsByRole(role);
        rolePermissionRepository.deleteAllByRole(role);
        rolePermissionRepository.flush();

        List<RolePermission> rolePermissions = new ArrayList<>();
        permissions.forEach(permissionCode -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(role);
            rolePermission.setPermissionCode(permissionCode);
            rolePermissions.add(rolePermission);
        });
        rolePermissionRepository.saveAll(rolePermissions);
        if (!existByRole) {
            List<String> userIds = userRepository.getIdsByRole(role.getCode());
            userIds.forEach(userId -> {
                rabbitMqPermissionProducer.save(new UserRolePermissionPayload(userId, role.getCode(), permissions));
            });
        }
    }

    @Transactional(readOnly = true)
    public List<String> getRolePermissions(Role role) {
        return rolePermissionRepository.findAllPermissionCodesByRole(role);
    }

    public void sync() {

        HttpHeaders headers = SecurityUtils.getHeader();
        headers.setBasicAuth(
            applicationProperties.getSupplyDevConfig().getUsername(),
            applicationProperties.getSupplyDevConfig().getPassword()
        );
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(applicationProperties.getSupplyDevConfig().getPermissionListUrl())
            .queryParam("page", 0)
            .queryParam("size", 10000)
            .queryParam("orderBy", "parentId")
            .queryParam("sortOrder", SortTypeEnum.desc.getName());

        ResponseEntity<List<SyncPermissionListDTO>> response = restTemplate.exchange(
            builder.toUriString(),
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<SyncPermissionListDTO>>() {}
        );

        if (response.getBody() != null && !CollectionUtils.isEmpty(response.getBody())) {
            List<Permission> permissions = new ArrayList<>();
            response
                .getBody()
                .forEach(permissionDTO -> {
                    Permission permission = permissionRepository
                        .findByCodeAndDeletedFalse(permissionDTO.getCode())
                        .orElse(new Permission());
                    permission.setName(permissionDTO.getName());
                    permission.setCode(permissionDTO.getCode());
                    permission.setPosition(permissionDTO.getPosition());
                    permission.setSection(permissionDTO.isSection());
                    permission.setPermissionType(permissionDTO.getPermissionType());
                    if (permissionDTO.getParent() != null) {
                        Permission parentPermission = permissionRepository
                            .findByCodeAndDeletedFalse(permissionDTO.getParent().getCode())
                            .orElse(new Permission());
                        parentPermission.setName(permissionDTO.getParent().getName());
                        parentPermission.setCode(permissionDTO.getParent().getCode());
                        parentPermission.setPosition(permissionDTO.getParent().getPosition());
                        parentPermission.setSection(permissionDTO.getParent().isSection());
                        parentPermission.setPermissionType(permissionDTO.getParent().getPermissionType());
                        permissionRepository.save(parentPermission);
                        permission.setParent(parentPermission);
                        permission.setParentId(parentPermission.getId());
                    }
                    permissions.add(permission);
                });
            if (!CollectionUtils.isEmpty(permissions)) {
                permissionRepository.saveAll(permissions);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<SyncPermissionListDTO> getAllList(BaseFilter filter) {
        return permissionRepository.getAll(PermissionType.valueOf(filter.getType()))
            .stream()
            .map(permission -> {
                SyncPermissionListDTO permissionListDTO = wrap(permission);
                if (permission.getParent() != null) {
                    permissionListDTO.setParent(wrap(permission.getParent()));
                }
                return permissionListDTO;
            }).collect(Collectors.toList());
    }

    private SyncPermissionListDTO wrap(Permission permission) {
        SyncPermissionListDTO permissionListDTO = new SyncPermissionListDTO();
        permissionListDTO.setId(permission.getId());
        permissionListDTO.setName(permission.getName());
        permissionListDTO.setCode(permission.getCode());
        permissionListDTO.setSection(permission.isSection());
        permissionListDTO.setPosition(permission.getPosition());
        return permissionListDTO;
    }

    public void createUserPermission(UserRolePermissionPayload userRolePermissionPayload) {
        List<UserPermission> userPermissions = new ArrayList<>();
        userRepository.findById(userRolePermissionPayload.getUserId())
            .ifPresent(
                user -> userRolePermissionPayload
                    .getPermissions()
                    .forEach(
                        permissionCode -> {
                            UserPermission userPermission = userPermissionRepository
                                .findByUserIdAndPermissionCode(user.getId(), permissionCode)
                                .orElse(new UserPermission());
                            if (userPermission.getId() == null) {
                                userPermission.setPermissionCode(permissionCode);
                                userPermission.setUserId(user.getId());
                                userPermissions.add(userPermission);
                            }
                        }
                    )
            );
        if (!CollectionUtils.isEmpty(userPermissions)) {
            userPermissionRepository.saveAll(userPermissions);
        }
    }
}
