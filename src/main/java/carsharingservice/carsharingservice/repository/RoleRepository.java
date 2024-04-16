package carsharingservice.carsharingservice.repository;

import carsharingservice.carsharingservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(Role.RoleName role);
}
