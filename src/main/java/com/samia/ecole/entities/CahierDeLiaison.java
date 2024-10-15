package com.samia.ecole.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cahier_de_liaison")
public class CahierDeLiaison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User teacher;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @Column(name = "teacher_name", nullable = false)
    private String teacherName;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(name = "local_date_time",nullable = false)
    private LocalDateTime dateTime;
    @Column(name = "modified_at",nullable = false)
    private LocalDateTime modifiedAt;

    public CahierDeLiaison(){}

    public CahierDeLiaison(User teacher, String teacherName, String title, String content, LocalDateTime dateTime, LocalDateTime modifiedAt, Student student) {
        this.teacher = teacher;
        this.teacherName = teacherName;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.modifiedAt = modifiedAt;
        this.student = student;
    }

    public CahierDeLiaison(Long id, User teacher, String teacherName, String title, String content, LocalDateTime dateTime, LocalDateTime modifiedAt, Student student) {
        this.id = id;
        this.teacher = teacher;
        this.teacherName = teacherName;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.modifiedAt = modifiedAt;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "CahierDeLiaison{" +
                "id=" + id +
                //", teacher=" + teacher +
                ", teacherName='" + teacherName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateTime=" + dateTime +
                ", modifiedAt=" + modifiedAt +
                //", student=" + student +
                '}';
    }
}
