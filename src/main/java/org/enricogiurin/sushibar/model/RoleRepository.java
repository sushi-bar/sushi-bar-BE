package org.enricogiurin.sushibar.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by enrico on 2/28/17.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);


}
