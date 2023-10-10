package com.edu.ruse.studypal.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author anniexp
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "files")
@Table(name = "files")
public class File {
    @Column(name = "file_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;
}
