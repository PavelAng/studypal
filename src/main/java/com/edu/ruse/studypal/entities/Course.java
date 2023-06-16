package com.edu.ruse.studypal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author anniexp
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "degree_id", nullable = false)
    private Degree degree;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinTable(name = "students_course",
            joinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "course_id",
                            nullable = true, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "student_id", referencedColumnName = "user_id",
                            nullable = true, updatable = true)})
    private List<User> courseStudents;

}
