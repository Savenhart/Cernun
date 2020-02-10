package fr.satysko.models;

import fr.satysko.utils.EBiome;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Embeddable
public class Biome {

	@Enumerated(EnumType.STRING)
	EBiome biome;
	@Transient
	String sBiome;

	public Biome(){}

	public Biome (float niv, float hum) {
		biome = EBiome.OCEAN;
		sBiome = "Ocean";
		if (niv >0) {
			biome = EBiome.PLAINE;
			sBiome = "Plaine";
		}
	}
		
	public String getBiome() {
		return sBiome;
	}
}
