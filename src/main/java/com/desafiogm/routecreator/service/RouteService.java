package com.desafiogm.routecreator.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafiogm.routecreator.domain.PlannedStop;
import com.desafiogm.routecreator.domain.Route;
import com.desafiogm.routecreator.domain.Vehicle;
import com.desafiogm.routecreator.repository.PlannedStopRepository;
import com.desafiogm.routecreator.repository.RouteRepository;
import com.desafiogm.routecreator.util.HarversineFunction;
import com.desafiogm.routecreator.util.RouteUpdater;

@Service
public class RouteService {

	@Autowired
	private RouteRepository repository;

	@Autowired
	private PlannedStopRepository PSrepository;

	@Autowired
	private RouteUpdater updater;

	public List<Route> findAll() {
		return repository.findAll();
	}

	public Route save(Route route) {
		Route routeBackUp = route;
		route = repository.save(route);
		try {
			save(route.getId(), routeBackUp.getPlannedStops());
		} catch (Exception e) {
			System.out.println("Route without plannedStops!");
		}
		return route;
	}

	public List<PlannedStop> save(Long routeId, List<PlannedStop> plannedStops) {

		Route route = repository.findById(routeId).get();

		for (PlannedStop stop : plannedStops) {
			stop.setRoute(route);
		}
		return PSrepository.saveAll(plannedStops);
	}

	public void deletePlannedStop(Long plannedStopId) {
		System.out.println("Servidor - Deletando(byId) PlannedStop id:" + plannedStopId);
		PSrepository.deleteById(plannedStopId);

	}

	public void deletePlannedStop(PlannedStop plannedStop) {
		System.out.println("Servidor - Deletando PlannedStop id:" + plannedStop.getId());
		PSrepository.delete(plannedStop);

	}

	public Route findById(Long id) {
		Route route = null;
		try {
			route = repository.findById(id).get();
		} catch (Exception e) {

		}
		return route;
	}

	public List<Route> saveRoutes(List<Route> list) {

		for (Route route : list) {
			try {
				Route backUp = route;
				route = repository.save(route);
				for (PlannedStop pStop : backUp.getPlannedStops()) {
					pStop.setRoute(route);
				}
				PSrepository.saveAll(backUp.getPlannedStops());
			} catch (NullPointerException ex) {
				System.out.println(ex.getMessage());
				break;
			}
		}

		return list;

	}

	public Boolean updateRoute(Vehicle vehicle) {
		Boolean routeUpdated = false;
		Route route = repository.findByAssignedVehicle(vehicle.getId());

		System.out.println(route.toString());

		if (route.getStatus().contains("Pendente")) {
			route.setStatus("Em progresso");
			routeUpdated = true;
		}

		if (!route.getStatus().contains("Concluída")) {
			int countCompletedPlannedStop = 0;
			for (PlannedStop plannedStop : route.getPlannedStops()) {
				if (!plannedStop.getStatus().contains("Concluída")) {
					Double distancia = HarversineFunction.distance(vehicle, plannedStop);
					System.out.println("Distancia da parada " + plannedStop.getDescription() + ":" + distancia);
					System.out.println("Raio de entrega: " + plannedStop.getDeliveryRadius());
					if (distancia <= plannedStop.getDeliveryRadius()) {
						System.out.println("Dentro do raio de entrega - " + plannedStop.getDescription());
						if (plannedStop.getStartTime() == null) {
							System.out.println("Primeira vez q coordenada está proxima desta parada");
							plannedStop.setStartTime(LocalDateTime.now());
							routeUpdated = true;
						} else {
							Duration duration = Duration.between(plannedStop.getStartTime(), LocalDateTime.now());
							System.out.println("Está a " + duration.toMinutes() + " minutos nessa parada");
							if (duration.toMinutes() >= 1) { /* alterar para 5 */
								System.out.println("Não é 1ª vez q coordenada está próxima desta parada");
								plannedStop.setStatus("Em atendimento");
								route.setCurrentStop(plannedStop);
								routeUpdated = true;
							}
						}
						routeUpdated = true;
					} else {
						
						if (plannedStop.getStatus().contentEquals("Em atendimento")) {
							System.out.println("Encerrando atendimento");
							plannedStop.setStatus("Concluída");
							countCompletedPlannedStop++;
							plannedStop.setEndedTime(LocalDateTime.now());
							routeUpdated = true;
							if (route.getLongestStop() == null) {
								route.setLongestStop(plannedStop);
								routeUpdated = true;
							}

							else {
								Duration duration1 = Duration.between(plannedStop.getStartTime(),
										plannedStop.getEndedTime());
								Duration duration2 = Duration.between(route.getLongestStop().getStartTime(),
										route.getLongestStop().getEndedTime());
								if (duration1.compareTo(duration2) > 0) {
									route.setLongestStop(plannedStop);
									routeUpdated = true;
								}
							}
						} else if (plannedStop.getStatus().contentEquals("Pendente")&&plannedStop.getStartTime()!=null) {
							System.out.println(
									"Parada Pendente - Coordenada fora da rota - " + plannedStop.getDescription());
							plannedStop.setStartTime(null);
							routeUpdated=true;
						}
					}
				} else
					countCompletedPlannedStop++;

				if (countCompletedPlannedStop == route.getPlannedStops().size()) {
					route.setStatus("Concluída");
					routeUpdated = true;
				}
			}

			if (routeUpdated) {
				System.out.println(route.toString());
				updater.turnOn(route, route.getId());
			}
		}
		return routeUpdated;
	}

}
