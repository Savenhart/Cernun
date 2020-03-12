package fr.satysko.cernun.utils;

public enum EBiome {
	OCEAN("Ocean", ""),
	OCEANTROPICAL("Ocean tropical", ""),
	BANQUISE("Banquise", ""),
	PLAGE("Plage", ""),
	PLAGEGLACE("Plage glacée", ""),
	PLAGETROPICALE("Plage tropicale", ""),
	MARAIS("Marais", ""),
	MARAISGLACE("Marais glacé", ""),
	MARAISTROPICAL("Marais tropical", ""),
	INLANDSIS("Inlandsis", ""),
	FORET("Forêt", ""),
	MANGROVE("Mangrove", ""),
	PERMAFROST("Permafrost", ""),
	FORETTROPICALE("Forêt tropicale", ""),
	JUNGLE("Jungle", ""),
	TOUNDRA("Toundra", ""),
	TAIGA("Taiga", ""),
	PLAINE("Plaine", ""),
	SAVANE("Savane", ""),
	DESERTGLACE("Désert glacé", ""),
	STEPPE("Steppe", ""),
	PAMPA("Pampa", ""),
	DESERT("Desert", ""),
	HAUTPLATEAU("Haut plateau", ""),
	HAUTEFORETENNEIGEE("Haute forêt enneigée", ""),
	PLATEAUENNEIGEE("Plateau enneigé", ""),
	PLATEAUROCHEUXENNEIGE("Plateau rocheux enneigé", ""),
	HAUTEJUNGLE("Haute jungle", ""),
	PLATEAUROCHEUX("Plateau rocheux", ""),
	HAUTEFORET("Haute forêt", ""),
	PLATEAUVOLVANIQUE("Plateau volcanique", ""),
	MONTAGNE("Montagne", ""),
	MONTAGNEFORETENNEIGEE("Montagne forêt enneigée", ""),
	MONTAGNEENNEIGEE("Montagne enneigée", ""),
	MONTAGNEROCHEUSEENNEIGEE("Montagne rocheuse enneigée", ""),
	MONTAGNEJUNGLE("Montagne jungle", ""),
	MONTAGNEROCHEUSE("Montagne rocheuse", ""),
	MONTAGNEFORET("Montagne forêt", ""),
	VOLCAN("Volcan", ""),
	;

	private String name;
	private String path;

	public String getName(){
		return this.name;
	}

	public String getPath(){
		return this.path;
	}

	EBiome(final String name, final String path) {
		this.name = name;
		this.path = path;
	}

	public static EBiome valueOfByName(String key) throws IllegalArgumentException{
		for(EBiome val : values()){
			if(val.getName().equals(key)){
				return val;
			}
		}
		throw new IllegalArgumentException("Il n'y a pas de biome nommé " + key);
	}
}
