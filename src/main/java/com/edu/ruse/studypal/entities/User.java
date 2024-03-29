package com.edu.ruse.studypal.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.List;

/**
 * @author anniexp
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 64, name = "password")
    private String password;

    @Size(max = 20)
    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;

    @Column(name = "role", nullable = false)
    private RoleEnum role;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    //if user is faculty coordinator, his coordinated faculties
    @ManyToMany(mappedBy = "coordinators", fetch = FetchType.LAZY)
    private List<Faculty> coordinatedFaculties;

    //if user is teacher, his faculties
    @ManyToMany(mappedBy = "facultyTeachers", fetch = FetchType.LAZY)
    private List<Faculty> teachingFaculties;

    //if user is org admin, his organizations
    @OneToMany(targetEntity = Organization.class, mappedBy = "adminOrg", cascade = CascadeType.ALL)
    private List<Organization> organization;

    //if user is student, his course, class
    @ManyToMany(mappedBy = "courseStudents", fetch = FetchType.LAZY)
    private List<Course> currentCourses;

    //if user is teacher, his subjects
    @ManyToMany(mappedBy = "subjectTeachers", fetch = FetchType.LAZY)
    private List<Subject> teachesSubjects;
}
