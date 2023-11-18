package com.edu.ruse.studypal.repositories;

import com.edu.ruse.studypal.entities.Organization;
import com.edu.ruse.studypal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author anniexp
 */
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByName(String name);

    Boolean existsByName(String name);
}
