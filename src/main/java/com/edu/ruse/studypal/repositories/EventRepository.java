package com.edu.ruse.studypal.repositories;

import com.edu.ruse.studypal.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author anniexp
 */
public interface EventRepository extends JpaRepository<Event, Long> {

}
