package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Method that find a user through username entered parameter
     * @param username
     * @return Optional User object
     */
    Optional<User> findByUsername(String username);

}
