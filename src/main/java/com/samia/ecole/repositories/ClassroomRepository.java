package com.samia.ecole.repositories;

import com.samia.ecole.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Classroom findByClassroomCode(String classroomCode);

    Optional<Classroom> findByIdAndClassroomCode(Long classroomId, String classroomCode);
}
