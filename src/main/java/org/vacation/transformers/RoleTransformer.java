package org.vacation.transformers;

import org.springframework.stereotype.Component;
import org.vacation.beans.RoleDto;
import org.vacation.models.Role;

@Component
public class RoleTransformer extends Transformer<RoleDto, Role>{

    @Override
    public RoleDto toDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getRoleId());
        roleDto.setRoleName(role.getRoleName());
        return roleDto;
    }

    @Override
    public Role toEntity(RoleDto roleDto) {
        Role role = new Role();
        role.setRoleName(roleDto.getRoleName());
        return role;
    }
}
