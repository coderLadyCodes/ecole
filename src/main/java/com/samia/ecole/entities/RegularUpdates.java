package com.samia.ecole.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User parent;
    @Column(name = "student_name")
    private String studentName;
    @Column(name = "local_date_time", nullable = false)
    private LocalDateTime localDateTime;
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;
    @Column(name = "local_date", nullable = false)
    private LocalDate localDate;
    @Column(name = "is_absent", nullable = true)
    private Boolean isAbsent;
    @Column(name = "has_cantine", nullable = true)
    private Boolean hasCantine;
    @Column(name="garderie", nullable = true)
    @Enumerated(EnumType.STRING)
    private Garderie garderie;
//    @Column(name="attachment")
//    private String attachment;
    public RegularUpdates() {
    }

    public RegularUpdates(Student student, User parent, String studentName, LocalDateTime localDateTime, LocalDateTime modifiedAt, LocalDate localDate, Boolean isAbsent, Boolean hasCantine, Garderie garderie) {
        this.student = student;
        this.parent = parent;
        this.studentName = studentName;
        this.localDateTime = localDateTime;
        this.modifiedAt = modifiedAt;
        this.localDate = localDate;
        this.isAbsent = isAbsent;
        this.hasCantine = hasCantine;
        this.garderie = garderie;
    }

    public RegularUpdates(Long id, Student student, User parent, String studentName, LocalDateTime localDateTime, LocalDateTime modifiedAt, LocalDate localDate, Boolean isAbsent, Boolean hasCantine, Garderie garderie) {
        this.id = id;
        this.student = student;
        this.parent = parent;
        this.studentName = studentName;
        this.localDateTime = localDateTime;
        this.modifiedAt = modifiedAt;
        this.localDate = localDate;
        this.isAbsent = isAbsent;
        this.hasCantine = hasCantine;
        this.garderie = garderie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Boolean getAbsent() {
        return isAbsent;
    }

    public void setAbsent(Boolean absent) {
        isAbsent = absent;
    }

    public Boolean getHasCantine() {
        return hasCantine;
    }

    public void setHasCantine(Boolean hasCantine) {
        this.hasCantine = hasCantine;
    }

    public Garderie getGarderie() {
        return garderie;
    }

    public void setGarderie(Garderie garderie) {
        this.garderie = garderie;
    }

    @Override
    public String toString() {
        return "RegularUpdates{" +
                "id=" + id +
                //", student=" + student +
                //", parent=" + parent +
                ", studentName='" + studentName + '\'' +
                ", localDateTime=" + localDateTime +
                ", modifiedAt=" + modifiedAt +
                ", localDate=" + localDate +
                ", isAbsent=" + isAbsent +
                ", hasCantine=" + hasCantine +
                ", garderie=" + garderie +
                '}';
    }
}
