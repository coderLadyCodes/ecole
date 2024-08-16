package com.samia.ecole.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "regular_updates")
public class RegularUpdates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "user_id", unique = false)
    private User parent;
    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;
    @Column(name = "is_absent", nullable = true)
    private Boolean isAbsent;
    @Column(name = "has_cantine", nullable = true)
    private Boolean hasCantine;
    @Column(name="garderie", nullable = true)
    @Enumerated(EnumType.STRING)
    private Garderie garderie;
}
