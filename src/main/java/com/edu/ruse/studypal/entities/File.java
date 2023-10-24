package com.edu.ruse.studypal.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

/**
 * @author anniexp
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "files")
@Table(name = "files")
public class File {
    @Column(name = "file_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = true)
    private String fileTypee;

    @Column(name = "file_content", nullable = true)
    private byte[] fileContent;

    @ManyToMany(mappedBy = "eventMaterials", fetch = FetchType.LAZY)
    private List<Event> materialInEvents;

    @ManyToMany(mappedBy = "eventExercises", fetch = FetchType.LAZY)
    private List<Event> exerciseInEvents;

    @ManyToMany(mappedBy = "eventSolutions", fetch = FetchType.LAZY)
    private List<Event> solutionInEvents;
}
