package com.edu.ruse.studypal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author anniexp
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "course")
@Table(name = "course")
public class Course {
    @Column(name = "course_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "degree_id", nullable = false)
    private Degree degree;
}
