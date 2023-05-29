package com.edu.ruse.studypal.repositories;

import com.edu.ruse.studypal.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author anniexp
 */
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
