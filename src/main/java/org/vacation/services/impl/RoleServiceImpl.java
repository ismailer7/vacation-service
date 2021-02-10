package org.vacation.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vacation.beans.RoleDto;
import org.vacation.models.Role;
import org.vacation.repositories.IRoleRepository;
import org.vacation.services.IRoleService;
import org.vacation.transformers.RoleTransformerImpl;

@Service
public class RoleServiceImpl extends RoleCRUDServiceImpl implements IRoleService {

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    RoleTransformerImpl roleTransformer;

    @Override
    public RoleDto findByRoleName(String roleName) {
        Role result = roleRepository.findByRoleName(roleName);
        if(result != null) {
            return roleTransformer.toDto(result);
        }
        return null;
    }
}
