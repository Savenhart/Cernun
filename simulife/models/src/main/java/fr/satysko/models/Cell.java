package fr.satysko.models;

public class Cell {

	Biome biome;
	
	public Cell(float niv, float hum) {
		biome = new Biome(niv, hum);
	}
	
	public String getBiome() {
		return biome.getBiome();
	}
}
