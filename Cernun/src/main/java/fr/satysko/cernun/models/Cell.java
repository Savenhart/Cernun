package fr.satysko.cernun.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.satysko.cernun.repositories.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"chunk_id", "posx", "posy"}))
public class Cell extends Entite {

	@Embedded
	private Location location;
	@Embedded
	private Biome biome;
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	private Picture picture;
	@ManyToOne
    @JsonIgnore
	private Chunk chunk;

	public Cell() {
	}

	public Cell(double niv, double hum, double tem) {
		biome = new Biome(niv, hum, tem);
		biome.definePath();
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

	public Chunk getChunk() {
		return chunk;
	}

	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
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
				"chunk=" + chunk +
				", location=" + location +
				", biome=" + biome +
				", picture=" + picture +
				'}';
	}
}
