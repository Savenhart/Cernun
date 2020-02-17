package fr.satysko.utils;

public enum EBiome {
	OCEAN("Ocean"),
	OCEANTROPICAL("Ocean tropical"),
	BANQUISE("Banquise"),
	PLAGE("Plage"),
	PLAGEGLACE("Plage glacée"),
	PLAGETROPICALE("Plage tropicale"),
	MARAIS("Marais"),
	MARAISGLACE("Marais glacé"),
	MARAISTROPICAL("Marais tropical"),
	INLANDSIS("Inlandsis"),
	FORET("Forêt"),
	MANGROVE("Mangrove"),
	PERMAFROST("Permafrost"),
	FORETTROPICALE("Forêt tropicale"),
	JUNGLE("Jungle"),
	TOUNDRA("Toundra"),
	TAIGA("Taiga"),
	PLAINE("Plaine"),
	SAVANE("Savane"),
	DESERTGLACE("Désert glacé"),
	STEPPE("Steppe"),
	PAMPA("Pampa"),
	DESERT("Désert"),
	HAUTPLATEAU("Haut plateau"),
	MONTAGNE("Montagne"),
	;

	private String name;

	public String getName(){
		return this.name;
	}

	private EBiome(String name) {
		this.name = name;
	}
}
