package roland.bookstore.user;


import org.springframework.data.jpa.repository.JpaRepository;
import roland.bookstore.user.model.ERole;
import roland.bookstore.user.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
