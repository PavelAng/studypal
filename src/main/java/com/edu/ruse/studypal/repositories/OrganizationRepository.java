package com.edu.ruse.studypal.repositories;

import com.edu.ruse.studypal.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
