package carsharingservice.carsharingservice.repository.user;

import carsharingservice.carsharingservice.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
