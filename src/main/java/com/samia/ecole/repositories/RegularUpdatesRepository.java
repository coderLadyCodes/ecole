package com.samia.ecole.repositories;

import com.samia.ecole.entities.RegularUpdates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegularUpdatesRepository extends JpaRepository<RegularUpdates, Long> {
    //I DIDN'T USE IT YET
    @Query("SELECT r from RegularUpdates r WHERE r.student.id = :studentId ORDER BY r.localDateTime DESC") 
    Optional<RegularUpdates> findLatestByStudentId(@Param("studentId") Long studentId);

    List<RegularUpdates> findByStudentId(Long studentId);
}
