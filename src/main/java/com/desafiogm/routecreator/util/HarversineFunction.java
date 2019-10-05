package com.desafiogm.routecreator.util;

import com.desafiogm.routecreator.domain.PlannedStop;
import com.desafiogm.routecreator.domain.Vehicle;

public class HarversineFunction {
	
	static public Double distance(Vehicle vehicle,PlannedStop plannedStop) {
		final int R = 6371000; // Radius of the earth	
	
		 Double lat1 = vehicle.getLat();
		 Double lon1 = vehicle.getLgn();
		 Double lat2 = plannedStop.getLat();
		 Double lon2 = plannedStop.getLng();
		 Double latDistance = toRad(lat2-lat1);
		 Double lonDistance = toRad(lon2-lon1);
		 Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
		 Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * 
		 Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		 Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		 Double distance = R * c;
		 return distance;
	}
	
	private static Double toRad(Double value) {
		 return value * Math.PI / 180;
		 }

}
