package com.edu.ruse.studypal.repositories;

import com.edu.ruse.studypal.entities.Degree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author anniexp
 */
public interface DegreeRepository extends JpaRepository<Degree, Long> {
    List<Degree> findByName(String degreeName);

    List<Degree> findByFaculty(String facultyName);
}
