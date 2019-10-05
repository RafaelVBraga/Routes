package com.desafiogm.routecreator.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.desafiogm.routecreator.domain.PlannedStop;
import com.desafiogm.routecreator.domain.Route;
import com.desafiogm.routecreator.domain.Vehicle;
import com.desafiogm.routecreator.service.RouteInFileService;
import com.desafiogm.routecreator.service.RouteService;

@RestController
public class RouteController {

	@Autowired
	private RouteService service;

	@Autowired
	private RouteInFileService serviceF;

	@GetMapping(value = "/routes")
	public ResponseEntity<?> toList() {
		List<Route> routesList = service.findAll();		
		return ResponseEntity.status(HttpStatus.OK).body(routesList);
	}

	@PostMapping(value = "/route")
	public ResponseEntity<Void> save(@RequestBody Route route) {
		route = service.save(route);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/route/{id}").buildAndExpand(route.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}

	@PostMapping(value = "/routes")
	public ResponseEntity<?>  saveRoutes(@RequestBody List<Route> routes) {
		try {
		List<Route> list = service.saveRoutes(routes);
		return ResponseEntity.status(HttpStatus.OK).body(list);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error saving routes!");
		}

	}
	
	@DeleteMapping(value = "/route/plannedStop/{id}")
	public  @ResponseBody ResponseEntity<?>  deletePlannedStop(@RequestBody PlannedStop plannedStop, @PathVariable("id") Long id) {
		
		System.out.println(plannedStop.toString());
		
		try {
			plannedStop.setId(id);
			service.deletePlannedStop(plannedStop);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete successfull!");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error deleting PlannedStop!");
		}

	}
	
	@PostMapping(value="/ReceiveCoordinate")
	public ResponseEntity<?> receiveCoordinate(@RequestBody Vehicle vehicle){
		service.updateRoute(vehicle);
		return ResponseEntity.status(HttpStatus.OK).body("Coordinates Received!");
	}

	@GetMapping(value = "/route/{id}")
	public ResponseEntity<?> toListRoute(@PathVariable("id") Long id) {
		
			Route route = service.findById(id);
		
		if (route == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(route);

	}

	@GetMapping(value = "/routes-file")
	public ResponseEntity<?> load() {

		try {
			List<Route> list = serviceF.loadAllRoutesInFile(); 
			service.saveRoutes(list);
			return ResponseEntity.status(HttpStatus.OK).body("Routes loaded!");
		} catch (IOException e) {

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error loading routes!");
		}
	}

	@PostMapping(value = "/routes-file")
	public ResponseEntity<?> save() {

		try {

			List<Route> list = service.findAll();
			serviceF.saveRouteListFile(list);
			return ResponseEntity.status(HttpStatus.OK).body("Routes saved in file!");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Error saving routes in file!");
		}
	}

}
