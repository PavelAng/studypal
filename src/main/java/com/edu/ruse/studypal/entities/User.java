package com.edu.ruse.studypal.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false, length = 64, name = "password")
    private String password;

    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;

    @Column(name = "role", nullable = false)
    private String rolee;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;
}
