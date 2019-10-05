package com.desafiogm.routecreator.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Vehicle implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonInclude 
	private Long id;
	
	@JsonInclude
	private Double lat;
	
	@JsonInclude
	private Double lgn;
	
	@JsonInclude
	private LocalDateTime time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLgn() {
		return lgn;
	}

	public void setLgn(Double lgn) {
		this.lgn = lgn;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Vehicle() {}

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
		if (!(obj instanceof Vehicle))
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
	

}
