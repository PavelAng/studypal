package com.edu.ruse.studypal.entities;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;

import java.time.Instant;
import java.util.List;

/**
 * @author anniexp
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "events")
@Table(name = "events")
public class Event {
    @Column(name = "event_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(name = "type", nullable = false)
    private EventTypeEnum type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    //instants from to duration of event
    @Column(name = "event_start", updatable = true)
    @Timestamp
    private Instant eventStart;

    @Column(name = "event_end", updatable = true)
    @Timestamp()
    private Instant eventEnd;

    //event files, exercises to students, student solutions
  /*  @Column(name = "files", nullable = true)
    private List<String> materials;

    @Column(name = "exercises", nullable = true)
    private List<String> exercises;

    @Column(name = "solutions", nullable = true)
    private List<String> solutions;*/

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinTable(name = "events_materials",
            joinColumns = {
                    @JoinColumn(name = "event_id", referencedColumnName = "event_id",
                            nullable = true, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "material_id",
                            nullable = true, updatable = true)})
    private List<File> eventMaterials;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinTable(name = "events_exercises",
            joinColumns = {
                    @JoinColumn(name = "event_id", referencedColumnName = "event_id",
                            nullable = true, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "exercise_id",
                            nullable = true, updatable = true)})
    private List<File> eventExercises;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinTable(name = "events_solutions",
            joinColumns = {
                    @JoinColumn(name = "event_id", referencedColumnName = "event_id",
                            nullable = true, updatable = true)},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "solution_id",
                            nullable = true, updatable = true)})
    private List<File> eventSolutions;

}
