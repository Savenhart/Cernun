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

	public Biome(){}

	public Biome (float niv, float hum, float tem) {
		if (niv > 0.85){
			biome = EBiome.MONTAGNE;
		}else if (niv > 0.55){
			biome = EBiome.HAUTPLATEAU;
		}else if (niv > -0.05){
			if(hum > 0.8){
				if(tem > 0.5){
					biome = EBiome.MANGROVE;
				}else if (tem > -0.5){
					biome = EBiome.FORET;
				}else{
					biome = EBiome.INLANDSIS;
				}
			}else if (hum > 0.5){
				if(tem > 0.8){
					biome = EBiome.JUNGLE;
				}else if (tem > 0.5){
					biome = EBiome.FORETTROPICALE;
				}else if (tem > -0.5){
					biome = EBiome.FORET;
				}else{
					biome = EBiome.PERMAFROST;
				}
			}else if (hum > -0.5){
				if (tem > 0.5){
					biome = EBiome.SAVANE;
				}else if (tem > -0.5){
					biome = EBiome.PLAINE;
				}else if (tem > -0.8){
					biome = EBiome.TAIGA;
				}else{
					biome = EBiome.TOUNDRA;
				}
			}else if (hum > -0.8){
				if(tem > 0.8){
					biome = EBiome.DESERT;
				}else if (tem > 0.5){
					biome = EBiome.PAMPA;
				}else if (tem > -0.5){
					biome = EBiome.PLAINE;
				}else if (tem > -0.8){
					biome = EBiome.STEPPE;
				}else{
					biome = EBiome.DESERTGLACE;
				}
			}else{
				if (tem > -0.5){
					biome = EBiome.DESERT;
				}else{
					biome = EBiome.DESERTGLACE;
				}
			}
		}else if (niv > -0.25){
			if(hum > 0.34){
				if (tem > 0.5){
					biome = EBiome.MARAISTROPICAL;
				}else if (tem > -0.5) {
					biome = EBiome.MARAIS;
				}else {
					biome = EBiome.MARAISGLACE;
				}
			}else {
				if (tem > 0.5){
					biome = EBiome.PLAGETROPICALE;
				}else if (tem > -0.5) {
					biome = EBiome.PLAGE;
				}else {
					biome = EBiome.PLAGEGLACE;
				}
			}
		}else{
			if (tem > 0.5){
				biome = EBiome.OCEANTROPICAL;
			}else if (tem > -0.5) {
				biome = EBiome.OCEAN;
			}else {
				biome = EBiome.BANQUISE;
			}
		}
		System.out.println("elevation : " + niv + " | humidite : " + hum + " | température : " + tem + " => " + getBiome());
	}
		
	public String getBiome() {
		return biome.toString();
	}
}
