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
    @Column(name = "files", nullable = true)
    private List<String> files;

    @Column(name = "exercises", nullable = true)
    private List<String> exercises;

    @Column(name = "solutions", nullable = true)
    private List<String> solutions;

}
