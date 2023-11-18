package com.edu.ruse.studypal.repositories;

import com.edu.ruse.studypal.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author anniexp
 */
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
