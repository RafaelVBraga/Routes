package com.desafiogm.routecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafiogm.routecreator.domain.Route;

public interface RouteRepository extends JpaRepository<Route, Long>{
	
	public Route findByAssignedVehicle(Long id);

}
