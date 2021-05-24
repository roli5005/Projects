package roland.bookstore.user;


import org.springframework.data.jpa.repository.JpaRepository;
import roland.bookstore.user.dto.UserDetailsImpl;
import roland.bookstore.user.dto.UserMinimalDTO;
import roland.bookstore.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
