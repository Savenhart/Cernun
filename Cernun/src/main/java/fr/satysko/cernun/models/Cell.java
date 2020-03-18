package fr.satysko.cernun.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.annotation.PostConstruct;
import javax.persistence.*;

@Entity
public class Cell extends Entite {
	@ManyToOne
	@JsonIgnore
	private World world;
	@Embedded
	private Location location;
	@Embedded
	Biome biome;
	@OneToOne
	private Picture picture;

	public Cell() {}

	public Cell(float niv, float hum, float tem) {
		biome = new Biome(niv, hum, tem);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Biome getBiome() {
		return biome;
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

	@PostLoad
	@PostConstruct
	private void postLoad(){
//		if(picture == null){
//			picture = new Picture();
//			if(biome.getPath() == null){
//				biome.definePath();
//			}
//			picture.setName(biome.getBiome());
//			picture.setIpath(biome.getPath());
//			picture.setExtension("png");
//		}
	}
}
