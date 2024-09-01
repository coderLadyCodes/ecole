package com.samia.ecole.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.samia.ecole.entities.Garderie;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegularUpdatesDTO {
    private Long id;
    private Long studentId;
    private Long parentId;
    private String studentName;
    @JsonProperty("local_date_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime localDateTime;
    @JsonProperty("modified_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime modifiedAt;
    @JsonProperty("local_date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate localDate;
    @JsonProperty("isAbsent")
    @JsonSerialize
    @JsonDeserialize()
    private Boolean isAbsent;
    @JsonProperty("hasCantine")
    @JsonSerialize
    @JsonDeserialize()
    private Boolean hasCantine;
    private Garderie garderie;
    //private String attachment;
    public RegularUpdatesDTO() {
    }

    public RegularUpdatesDTO(Long studentId, Long parentId, String studentName, LocalDateTime localDateTime, LocalDateTime modifiedAt, LocalDate localDate, Boolean isAbsent, Boolean hasCantine, Garderie garderie) {
        this.studentId = studentId;
        this.parentId = parentId;
        this.studentName = studentName;
        this.localDateTime = localDateTime;
        this.modifiedAt = modifiedAt;
        this.localDate = localDate;
        this.isAbsent = isAbsent;
        this.hasCantine = hasCantine;
        this.garderie = garderie;
    }

    public RegularUpdatesDTO(Long id, Long studentId, Long parentId, String studentName, LocalDateTime localDateTime, LocalDateTime modifiedAt, LocalDate localDate, Boolean isAbsent, Boolean hasCantine, Garderie garderie) {
        this.id = id;
        this.studentId = studentId;
        this.parentId = parentId;
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
        return "RegularUpdatesDTO{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", parentId=" + parentId +
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
