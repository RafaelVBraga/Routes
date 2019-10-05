package com.desafiogm.routecreator.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.desafiogm.routecreator.domain.Route;
import com.desafiogm.routecreator.repository.RouteRepository;

@Component
public class RouteUpdater implements Runnable{
	
	@Autowired
	private RouteRepository repository;
	
	private Route route;
	
	private Long id;
	
	
	
	public RouteUpdater() {}

	public void turnOn(Route route, Long id) {
		
		this.route = route;
		this.id = id;
		run();
	}
	
	

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void run() {
		route.setId(id);
		repository.save(route);		
	}

}
