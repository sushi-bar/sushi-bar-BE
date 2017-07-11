package org.enricogiurin.sushibar.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by enrico on 2/28/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByEmail(String email);

    List<User> findByEmailAndConfirmationCode(String email, String confirmationCode);

}
