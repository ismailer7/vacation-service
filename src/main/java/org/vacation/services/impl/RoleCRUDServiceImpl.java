package org.vacation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.vacation.beans.RoleDto;
import org.vacation.models.Role;
import org.vacation.repositories.IRoleRepository;
import org.vacation.services.ICRUDService;
import org.vacation.transformers.RoleTransformerImpl;

public class RoleCRUDServiceImpl implements ICRUDService<RoleDto, Long> {

    @Autowired
    RoleTransformerImpl roleTransformer;

    @Autowired
    IRoleRepository roleRepository;

    @Override
    public RoleDto create(RoleDto roleDto) {
        Role role = roleTransformer.toEntity(roleDto);
        Role result = roleRepository.save(role);
        return roleTransformer.toDto(result);
    }

    @Override
    public RoleDto getById(Long aLong) {
        return null;
    }

    @Override
    public RoleDto update(Long aLong, RoleDto roleDto) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
