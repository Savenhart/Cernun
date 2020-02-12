package fr.satysko.models;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Cell extends Entite {
	@ManyToOne
	private World world;
	@Embedded
	private Location pos;
	@Embedded
	Biome biome;
	@OneToOne
	private Picture picture;
	
	public Cell(float niv, float hum) {
		biome = new Biome(niv, hum);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Location getPos() {
		return pos;
	}

	public void setPos(Location pos) {
		this.pos = pos;
	}

	public String getBiome() {
		return biome.getBiome();
	}

	public void setBiome(Biome biome) {
		this.biome = biome;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
}
