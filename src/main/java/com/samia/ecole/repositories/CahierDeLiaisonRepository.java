package com.samia.ecole.repositories;

import com.samia.ecole.entities.CahierDeLiaison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CahierDeLiaisonRepository extends JpaRepository<CahierDeLiaison, Long> {
    List<CahierDeLiaison> findByStudentId(Long studentId);
}
