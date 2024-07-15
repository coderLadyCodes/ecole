package com.samia.ecole.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "classrooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    private Grade grade;
    private Long userId;
    public Classroom() {
    }

    public Classroom(Grade grade, Long userId) {
        this.grade = grade;
        this.userId = userId;
    }

    public Classroom(Long id, Grade grade, Long userId) {
        this.id = id;
        this.grade = grade;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", grade=" + grade +
                ", userId=" + userId +
                '}';
    }
}
