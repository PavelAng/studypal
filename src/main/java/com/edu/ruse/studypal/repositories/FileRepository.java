package com.edu.ruse.studypal.repositories;

import com.edu.ruse.studypal.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFileName(String filename);
}
