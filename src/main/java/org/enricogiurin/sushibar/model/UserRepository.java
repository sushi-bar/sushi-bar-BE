package org.enricogiurin.sushibar.model;

import org.enricogiurin.sushibar.util.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Optional;

/**
 * Created by enrico on 2/28/17.
 */
@Secured(value = {Role.ROLE_USER})
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmailAndConfirmationCode(String email, String confirmationCode);

    List<User> activeUsers();

}
