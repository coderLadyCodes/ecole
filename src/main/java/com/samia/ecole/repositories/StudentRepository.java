package com.samia.ecole.repositories;

import com.samia.ecole.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
 @Query("FROM Student s WHERE s.user.id = :userId")
 List<Student> getStudentsByUserId(Long userId);
}
