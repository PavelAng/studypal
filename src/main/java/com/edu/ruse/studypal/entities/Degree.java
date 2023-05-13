package com.edu.ruse.studypal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "degree")
@Table(name = "degree")
public class Degree {
    @Column(name = "degree_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long degreeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "fac_id", nullable = false)
    private Faculty faculty;

    @OneToMany(targetEntity = Course.class, mappedBy = "degree")
    private List<Course> coursesList;

}
