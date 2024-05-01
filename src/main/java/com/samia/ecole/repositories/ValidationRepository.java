package com.samia.ecole.repositories;

import com.samia.ecole.entities.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {
    Optional<Validation> findByCode(String code);

    void deleteByExpirationBefore(Instant now);
}
