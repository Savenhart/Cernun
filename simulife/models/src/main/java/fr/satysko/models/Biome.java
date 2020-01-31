package fr.satysko.models;

import fr.satysko.utils.EBiomes;

public class Biome {
	
	EBiomes biome;
	public Biome (float niv, float hum) {
		biome = EBiomes.OCEAN;
		if (niv >0) {
			biome = EBiomes.PLAINE; 
		}
	}
		
	public String getBiome() {
		switch(biome) {
			case OCEAN:
				return "Ocean";
			case PLAINE:
				return "Plaine";
		}
		return "";
	}			

}
