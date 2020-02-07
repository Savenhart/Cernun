package fr.satysko.models;

public class Cell {
	private int worldID;
	private int posX;
	private int posY;
	Biome biome;
	private String picture;
	
	public Cell(float niv, float hum) {
		biome = new Biome(niv, hum);
	}
	
	public String getBiome() {
		return biome.getBiome();
	}
}
