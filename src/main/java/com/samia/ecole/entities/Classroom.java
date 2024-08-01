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

    private String teacher;
    @Column(name = "classroom_code", unique = true)
    private String classroomCode;
    public Classroom() {
    }

    public Classroom(Grade grade, Long userId, String teacher, String classroomCode) {
        this.grade = grade;
        this.userId = userId;
        this.teacher = teacher;
        this.classroomCode = classroomCode;
    }

    public Classroom(Long id, Grade grade, Long userId, String teacher, String classroomCode) {
        this.id = id;
        this.grade = grade;
        this.userId = userId;
        this.teacher = teacher;
        this.classroomCode = classroomCode;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(String classroomCode) {
        this.classroomCode = classroomCode;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", grade=" + grade +
                ", userId=" + userId +
                ", teacher='" + teacher + '\'' +
                ", classroomCode='" + classroomCode + '\'' +
                '}';
    }
}
