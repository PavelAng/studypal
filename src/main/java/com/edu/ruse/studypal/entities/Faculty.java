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
@Entity(name = "faculty")
@Table(name = "faculty")
public class Faculty {
    @Column(name = "fac_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facId;

    @Column(nullable = false)
    private String name;

    @Column(name = "coordinator", nullable = false)
    private Long coordinator;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToMany(targetEntity = Degree.class, mappedBy = "faculty")
    private List<Degree> degreeList;
}
