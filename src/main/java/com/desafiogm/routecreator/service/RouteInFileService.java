package com.desafiogm.routecreator.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.desafiogm.routecreator.domain.PlannedStop;
import com.desafiogm.routecreator.domain.Route;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class RouteInFileService {
	
   /* ---------------- LOCAL STORAGE  ----------------------------*/
	public List<Route> loadAllRoutesInFile() throws IOException {
		//ObjectInputStream input = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Route[] routesJson = objectMapper.readValue(new File("routes.json"), Route[].class);
			/*
			File file = new File("routes.dat");			
			input = new ObjectInputStream(new FileInputStream(file));
			List<Route> routes = (List<Route>) input.readObject();
			return routes;
			*/
			List<Route> list = Arrays.asList(routesJson);
			for(Route route : list )
				for(PlannedStop stop: route.getPlannedStops())
					stop.setRoute(route);
			return list;

		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}finally {
			//input.close();
		}

	}	
	
	public void saveRouteListFile(List<Route> list) throws IOException {
		//ObjectOutputStream output = null;
		ObjectMapper objectMapper = new ObjectMapper();
		for(Route route : list) {
			route.setId(null);
			for(PlannedStop PS: route.getPlannedStops()) {
				PS.setId(null);
			}
		}
		try {
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
			File file = new File("routes.json");
			objectMapper.writeValue(file, list);
			/*File file = new File("routes.dat");			
			output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(list);*/
			System.out.println("List save at:"+file.getAbsolutePath());

		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			//output.close();
		}

	}
	
}
