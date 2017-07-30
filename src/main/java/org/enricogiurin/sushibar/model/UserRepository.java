package org.enricogiurin.sushibar.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by enrico on 2/28/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAndConfirmationCode(String email, String confirmationCode);

    List<User> activeUsers();

}
