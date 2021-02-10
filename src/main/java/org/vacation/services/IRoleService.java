package org.vacation.services;

import org.vacation.beans.RoleDto;

public interface IRoleService {

    RoleDto findByRoleName(String roleName);
}
