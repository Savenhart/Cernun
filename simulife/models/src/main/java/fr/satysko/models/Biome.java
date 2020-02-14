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

	public Biome (float niv, float hum, float tem) {
		if (niv > 0.85){
			biome = EBiome.MONTAGNE;
			sBiome = "Montagne";
		}else if (niv > 0.55){
			biome = EBiome.HAUTPLATEAU;
			sBiome = "Haut plateau";
		}else if (niv > -0.05){
			if(hum > 0.8){
				if(tem > 0.5){
					biome = EBiome.MANGROVE;
					sBiome = "Mangrove";
				}else if (tem > -0.5){
					biome = EBiome.FORET;
					sBiome = "Forêt";
				}else{
					biome = EBiome.INLANDSIS;
					sBiome = "Inlandsis";
				}
			}else if (hum > 0.5){
				if(tem > 0.8){
					biome = EBiome.JUNGLE;
					sBiome = "Jungle";
				}else if (tem > 0.5){
					biome = EBiome.FORETTROPICALE;
					sBiome = "Forêt tropicale";
				}else if (tem > -0.5){
					biome = EBiome.FORET;
					sBiome = "Forêt";
				}else{
					biome = EBiome.PERMAFROST;
					sBiome = "Permafrost";
				}
			}else if (hum > -0.5){
				if (tem > 0.5){
					biome = EBiome.SAVANE;
					sBiome = "Savane";
				}else if (tem > -0.5){
					biome = EBiome.PLAINE;
					sBiome = "Plaine";
				}else if (tem > -0.8){
					biome = EBiome.TAIGA;
					sBiome = "Taiga";
				}else{
					biome = EBiome.TOUNDRA;
					sBiome = "Toundra";
				}
			}else if (hum > -0.8){
				if(tem > 0.8){
					biome = EBiome.DESERT;
					sBiome = "Désert";
				}else if (tem > 0.5){
					biome = EBiome.PAMPA;
					sBiome = "Pampa";
				}else if (tem > -0.5){
					biome = EBiome.PLAINE;
					sBiome = "Plaine";
				}else if (tem > -0.8){
					biome = EBiome.STEPPE;
					sBiome = "Steppe";
				}else{
					biome = EBiome.DESERTGLACE;
					sBiome = "Désert Glacé";
				}
			}else{
				if (tem > -0.5){
					biome = EBiome.DESERT;
					sBiome = "Désert";
				}else{
					biome = EBiome.DESERTGLACE;
					sBiome = "Désert Glacé";
				}
			}
		}else if (niv > -0.25){
			if(hum > 0.34){
				biome = EBiome.MARAIS;
				sBiome = "Marais";

				if (tem > 0.5){
					biome = EBiome.MARAISTROPICAL;
					sBiome = "Marais Tropical";
				}else if (tem > -0.5) {
					biome = EBiome.MARAIS;
					sBiome = "Marais";
				}else {
					biome = EBiome.MARAISGLACE;
					sBiome = "Marais glacé";
				}
			}else {
				if (tem > 0.5){
					biome = EBiome.PLAGETROPICALE;
					sBiome = "Plage Tropicale";
				}else if (tem > -0.5) {
					biome = EBiome.PLAGE;
					sBiome = "Plage";
				}else {
					biome = EBiome.PLAGEGLACE;
					sBiome = "Plage glacée";
				}
			}
		}else{
			if (tem > 0.5){
				biome = EBiome.OCEANTROPICAL;
				sBiome = "Ocean tempéré";
			}else if (tem > -0.5) {
				biome = EBiome.OCEAN;
				sBiome = "Ocean";
			}else {
				biome = EBiome.BANQUISE;
				sBiome = "Banquise";
			}
		}
		System.out.println("elevation : " + niv + " | humidite : " + hum + " | température : " + tem + " => " + sBiome);
	}
		
	public String getBiome() {
		return sBiome;
	}
}
