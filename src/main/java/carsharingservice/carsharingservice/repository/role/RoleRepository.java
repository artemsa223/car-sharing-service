package carsharingservice.carsharingservice.repository.role;

import carsharingservice.carsharingservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(Role.RoleName role);
}
