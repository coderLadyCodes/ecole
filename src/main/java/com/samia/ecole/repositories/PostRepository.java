package com.samia.ecole.repositories;

import com.samia.ecole.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("FROM Post p WHERE p.user.id = :userId")
    List<Post> getPostsByUserId(Long userId);
    @Query("SELECT p FROM Post p JOIN p.user u WHERE u.classroomId = :classroomId")
    List<Post> getPostByClassroomId(@Param("classroomId") Long classroomId);
}
