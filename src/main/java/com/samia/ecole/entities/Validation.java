package com.samia.ecole.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "validation")
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creation")
    private Instant creation;
    @Column(name = "expiration")
    private Instant expiration;
    @Column(name = "activation")
    private Instant activation;
    @Column(name = "code")
    private String code;
    @OneToOne(targetEntity = User.class,cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Validation() {
    }

    public Validation(Instant creation, Instant expiration, Instant activation, String code, User user) {
        this.creation = creation;
        this.expiration = expiration;
        this.activation = activation;
        this.code = code;
        this.user = user;
    }

    public Validation(Long id, Instant creation, Instant expiration, Instant activation, String code, User user) {
        this.id = id;
        this.creation = creation;
        this.expiration = expiration;
        this.activation = activation;
        this.code = code;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreation() {
        return creation;
    }

    public void setCreation(Instant creation) {
        this.creation = creation;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public Instant getActivation() {
        return activation;
    }

    public void setActivation(Instant activation) {
        this.activation = activation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Validation{" +
                "id=" + id +
                ", creation=" + creation +
                ", expiration=" + expiration +
                ", activation=" + activation +
                ", code='" + code + '\'' +
                ", user=" + user +
                '}';
    }
}
