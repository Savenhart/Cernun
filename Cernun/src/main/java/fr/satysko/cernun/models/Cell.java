package fr.satysko.cernun.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.satysko.cernun.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"world_id", "posx", "posy"}))
public class Cell extends Entite {

	@ManyToOne
	@JsonIgnore
	private World world;
	@Embedded
	private Location location;
	@Embedded
	private Biome biome;
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	private Picture picture;

	public Cell() {
	}

	public Cell(double niv, double hum, double tem) {
		biome = new Biome(niv, hum, tem);
		biome.definePath();
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
		if(biome.getPath() == null){
			biome.definePath();
		}
	}

	@Override
	public String toString() {
		return "Cell{" +
				"world=" + world +
				", location=" + location +
				", biome=" + biome +
				", picture=" + picture +
				'}';
	}
}
