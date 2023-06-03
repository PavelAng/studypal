package com.edu.ruse.studypal.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author anniexp
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "faculty")
@Table(name = "faculty")
public class Faculty {
    @Column(name = "fac_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facId;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToMany(targetEntity = Degree.class, mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Degree> degreeList;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinTable(name = "teachers_faculties",
            joinColumns = {
                    @JoinColumn(name = "faculty_Id", referencedColumnName = "fac_id",
                            nullable = true, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "teacher_id", referencedColumnName = "user_id",
                            nullable = true, updatable = true)})
    private List<User> facultyTeachers;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "coordinator_faculty",
            joinColumns = {
                    @JoinColumn(name = "faculty_Id", referencedColumnName = "fac_id",
                            nullable = true, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "coordinator_id", referencedColumnName = "user_id",
                            nullable = true, updatable = true)})
    private List<User> coordinators;
}
