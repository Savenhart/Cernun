package fr.satysko.cernun.models;

import fr.satysko.cernun.utils.EBiome;
import fr.satysko.cernun.utils.OpenSimplexNoise;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.Random;

@Embeddable
public class Biome {

	@Enumerated(EnumType.STRING)
	EBiome biome;
	@Transient
	String path;

	public Biome(){}

	public Biome (float niv, float hum, float tem) {
		defineBiome(niv, hum, tem);
	}

	public String getBiome() {
		return biome.getName();
	}

	public void setBiome(String key) {
		biome = EBiome.valueOfByName(key);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void defineBiome(float niv, float hum, float tem){
		if (niv > 0.85){
			if(tem > 0.8){
				biome = EBiome.VOLCAN;
			}else if(tem > 0.5){
				if(hum > 0.5){
					biome = EBiome.MONTAGNEJUNGLE;
				}else if(hum > -0.5){
					biome = EBiome.MONTAGNE;
				}else{
					biome = EBiome.MONTAGNEROCHEUSE;
				}
			}else if(tem > -0.5){
				if(hum > 0.8){
					biome = EBiome.MONTAGNEJUNGLE;
				}else if(hum > 0.5){
					biome = EBiome.MONTAGNEFORET;
				}else if(hum > -0.5){
					biome = EBiome.MONTAGNE;
				}else{
					biome = EBiome.MONTAGNEROCHEUSE;
				}
			}else{
				if(hum > 0.5){
					biome = EBiome.MONTAGNEFORETENNEIGEE;
				}else if(hum > -0.8){
					biome = EBiome.MONTAGNEENNEIGEE;
				}else{
					biome = EBiome.MONTAGNEROCHEUSEENNEIGEE;
				}
			}
		}else if (niv > 0.55){
			if(tem > 0.8){
				biome = EBiome.PLATEAUVOLVANIQUE;
			}else if(tem > 0.5){
				if(hum > 0.5){
					biome = EBiome.HAUTEJUNGLE;
				}else if(hum > -0.5){
					biome = EBiome.HAUTPLATEAU;
				}else{
					biome = EBiome.PLATEAUROCHEUX;
				}
			}else if(tem > -0.5){
				if(hum > 0.8){
					biome = EBiome.HAUTEJUNGLE;
				}else if(hum > 0.5){
					biome = EBiome.HAUTEFORET;
				}else if(hum > -0.5){
					biome = EBiome.HAUTPLATEAU;
				}else{
					biome = EBiome.PLATEAUROCHEUX;
				}
			}else{
				if(hum > 0.5){
					biome = EBiome.HAUTEFORETENNEIGEE;
				}else if(hum > -0.8){
					biome = EBiome.PLATEAUENNEIGEE;
				}else{
					biome = EBiome.PLATEAUROCHEUXENNEIGE;
				}
			}
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
	}

	public void definePath(){
		String[] lstPath = biome.getPath();
		int index = new Random().nextInt(lstPath.length);
		path = lstPath[index];
	}

	@PostLoad
	@PostConstruct
	private void postLoad(){
		if(path == null) {
			definePath();
		}
	}

	@Override
	public String toString() {
		return "Biome " + biome + " path : " + path;
	}
}
