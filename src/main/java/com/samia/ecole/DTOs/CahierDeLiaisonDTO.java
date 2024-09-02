package com.samia.ecole.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class CahierDeLiaisonDTO {
    private Long id;
    private Long teacherId;
    private Long studentId;
    private String teacherName;
    private String title;
    private String content;
    @JsonProperty("local_date_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateTime;
    @JsonProperty("modified_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime modifiedAt;
    public CahierDeLiaisonDTO(){}

    public CahierDeLiaisonDTO(Long teacherId, Long studentId, String teacherName, String title, String content, LocalDateTime dateTime, LocalDateTime modifiedAt) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.teacherName = teacherName;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.modifiedAt = modifiedAt;
    }

    public CahierDeLiaisonDTO(Long id, Long teacherId, Long studentId, String teacherName, String title, String content, LocalDateTime dateTime, LocalDateTime modifiedAt) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.teacherName = teacherName;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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

    @Override
    public String toString() {
        return "CahierDeLiaisonDTO{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", studentId=" + studentId +
                ", teacherName='" + teacherName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateTime=" + dateTime +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
