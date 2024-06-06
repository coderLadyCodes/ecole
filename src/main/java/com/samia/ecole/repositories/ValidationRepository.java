package com.samia.ecole.repositories;

import com.samia.ecole.entities.User;
import com.samia.ecole.entities.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {
    Optional<Validation> findByCode(String code);
    Optional<Validation> findByUser(User user);
    void deleteByExpirationBefore(Instant now);
}
