package org.vacation.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vacation.models.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
