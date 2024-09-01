package com.samia.ecole.repositories;

import com.samia.ecole.entities.Grade;
import com.samia.ecole.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
 @Query("FROM Student s WHERE s.user.id = :userId")
 List<Student> getStudentsByUserId(Long userId);
 Optional<Student> findByIdAndClassroomId(Long id, Long classroomId);

 List<Student> findAllByClassroomId(Long classroomId);
@Query("SELECT s FROM Student s WHERE s.classroomId = :classroomId AND s.grade = :grade")
 List<Student> findByClassroomIdAndGrade(@Param("classroomId") Long classroomId,  @Param("grade") Grade grade);
}
