package com.edu.ruse.studypal.repositories;

import com.edu.ruse.studypal.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author anniexp
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
}
