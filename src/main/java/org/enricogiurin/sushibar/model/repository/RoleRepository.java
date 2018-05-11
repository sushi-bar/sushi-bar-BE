package org.enricogiurin.sushibar.model.repository;

import org.enricogiurin.sushibar.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
