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
@Entity(name = "subject")
@Table(name = "subject")
public class Subject {
    @Column(name = "subject_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column
    private List<String> topics;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinTable(name = "teachers_subjects",
            joinColumns = {
                    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id",
                            nullable = true, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "teacher_id", referencedColumnName = "user_id",
                            nullable = true, updatable = true)})
    private List<User> subjectTeachers;

    //to do - add one to many to events
    @OneToMany(targetEntity = Event.class, mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Event> events;
}
