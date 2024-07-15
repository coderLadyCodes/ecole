package com.samia.ecole.repositories;

import com.samia.ecole.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("FROM User u where u.classroomId = :classroomId")
    List<User> findByClassroomId(Long classroomId);
}
