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
@Entity(name = "organization")
@Table(name = "organization")
public class Organization {
    @Column(name = "organization_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(targetEntity = Degree.class, mappedBy = "organization")
    private List<Degree> degreeList;
}
