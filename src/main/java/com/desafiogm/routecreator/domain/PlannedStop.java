package com.desafiogm.routecreator.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class PlannedStop implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JsonInclude(Include.NON_NULL)
	private String description;
	
	@JsonInclude(Include.NON_NULL)
	private double lat;
	
	@JsonInclude(Include.NON_NULL)
	private double lng;
	
	@JsonInclude(Include.NON_NULL)
	private double deliveryRadius;
	
	@JsonInclude(Include.NON_NULL)
	private String status;//  - >// Pendente, Em progresso, Concluída;
	
	@JsonInclude(Include.NON_NULL)
	private LocalDateTime startTime;
	
	@JsonInclude(Include.NON_NULL)
	private LocalDateTime endedTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROUTE_ID")
	@JsonIgnore
	private Route route;

	
	

	public PlannedStop() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getDeliveryRadius() {
		return deliveryRadius;
	}

	public void setDeliveryRadius(double deliveryRadius) {
		this.deliveryRadius = deliveryRadius;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndedTime() {
		return endedTime;
	}

	public void setEndedTime(LocalDateTime endedTime) {
		this.endedTime = endedTime;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}	

	@Override
	public int hashCode() {
		return Objects.hash(description, lat, lng);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PlannedStop))
			return false;
		PlannedStop other = (PlannedStop) obj;
		return Objects.equals(description, other.description)
				&& Double.doubleToLongBits(lat) == Double.doubleToLongBits(other.lat)
				&& Double.doubleToLongBits(lng) == Double.doubleToLongBits(other.lng);
	}
	
	
	

}
