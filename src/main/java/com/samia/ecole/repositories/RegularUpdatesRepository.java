package com.samia.ecole.repositories;

import com.samia.ecole.entities.RegularUpdates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularUpdatesRepository extends JpaRepository<RegularUpdates, Long> {

}
