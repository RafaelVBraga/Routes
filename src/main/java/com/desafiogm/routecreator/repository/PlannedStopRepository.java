package com.desafiogm.routecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafiogm.routecreator.domain.PlannedStop;

@Repository
public interface PlannedStopRepository extends JpaRepository<PlannedStop, Long>{

}
