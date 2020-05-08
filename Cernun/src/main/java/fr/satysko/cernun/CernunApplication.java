package fr.satysko.cernun;

import fr.satysko.cernun.controllers.SimulationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CernunApplication {

	private static SimulationController simulationController;

	@Autowired
	SimulationController controller;

	public static void main(String[] args) {
		SpringApplication.run(CernunApplication.class, args);
		try {
			simulationController.loop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void postConstruct(){
		simulationController = controller;
	}

}
