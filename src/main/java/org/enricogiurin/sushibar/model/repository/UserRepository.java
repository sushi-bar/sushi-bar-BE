package org.enricogiurin.sushibar.model.repository;

import org.enricogiurin.sushibar.model.User;
import org.enricogiurin.sushibar.util.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.Optional;


@Secured(value = {Role.ROLE_USER})
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmailAndConfirmationCode(String email, String confirmationCode);

    List<User> activeUsers();

}
