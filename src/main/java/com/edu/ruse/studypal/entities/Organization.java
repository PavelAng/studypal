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
@Entity(name = "organization")
@Table(name = "organization")
public class Organization {
    @Column(name = "organization_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(targetEntity = Faculty.class, mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Faculty> facultyList;

    @ManyToOne
    @JoinColumn(name = "admin_org_id", nullable = true)
    private User adminOrg;
}
