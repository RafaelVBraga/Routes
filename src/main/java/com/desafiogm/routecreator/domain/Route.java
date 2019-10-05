package com.desafiogm.routecreator.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@Entity
public class Route implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JsonInclude(Include.NON_NULL)
	private String routePlan;
	
	@JsonInclude(Include.NON_NULL)
	private Long assignedVehicle;
	
	@JsonInclude(Include.NON_NULL)
	@OneToMany(mappedBy = "route")
	private List<PlannedStop> plannedStops;
	
	@JsonInclude(Include.NON_NULL)
	private String status; // Pendente, Em progresso, Concluída
	
	@JsonInclude(Include.NON_NULL)
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "longestStop_id", referencedColumnName = "id")
	private PlannedStop longestStop;
	
	@JsonInclude(Include.NON_NULL)	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currentStop_id", referencedColumnName = "id")
	private PlannedStop currentStop;
	
	public Route() {}
	
	public Route(String routePlan) {
		
		this.routePlan = routePlan;
		this.plannedStops = new ArrayList<>();
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoutePlan() {
		return routePlan;
	}

	public void setRoutePlan(String routePlan) {
		this.routePlan = routePlan;
	}

	public Long getAssignedVehicle() {
		return assignedVehicle;
	}

	public void setAssignedVehicle(Long assignedVehicle) {
		this.assignedVehicle = assignedVehicle;
	}

	public List<PlannedStop> getPlannedStops() {
		return plannedStops;
	}

	public void setPlannedStops(List<PlannedStop> plannedStops) {
		this.plannedStops = plannedStops;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PlannedStop getLongestStop() {
		return longestStop;
	}

	public void setLongestStop(PlannedStop longestStop) {
		this.longestStop = longestStop;
	}

	public PlannedStop getCurrentStop() {
		return currentStop;
	}

	public void setCurrentStop(PlannedStop currentStop) {
		this.currentStop = currentStop;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ",\n routePlan=" + routePlan + ",\n assignedVehicle=" + assignedVehicle
				+ ",\n plannedStopsNumber=" +((plannedStops!=null)? plannedStops.size():"null") + ",\n status=" + status + ",\n longestStop=" + ((longestStop!=null)?longestStop.getDescription():"null")
				+ ",\n currentStop=" + ((currentStop!=null)?currentStop.getDescription():"null") + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Route))
			return false;
		Route other = (Route) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
