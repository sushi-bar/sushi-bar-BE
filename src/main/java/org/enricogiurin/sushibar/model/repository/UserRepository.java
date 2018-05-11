package org.enricogiurin.sushibar.model.repository;

import org.enricogiurin.sushibar.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmailAndConfirmationCode(String email, String confirmationCode);

}
