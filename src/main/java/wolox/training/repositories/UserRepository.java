package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
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

    /**
     * Method that find a user list according to the birthdate is between startDate and endDate entered parameter and the name containing the entered parameter
     * @param startDate
     * @param endDate
     * @param name
     * @return
     */
    Optional<List<User>> findByBirthdateBetweenAndNameContainingIgnoreCase(LocalDate startDate, LocalDate endDate, String name);

}
