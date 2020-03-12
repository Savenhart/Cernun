package fr.satysko.cernun.utils;

public enum EBiome {
	OCEAN("Ocean", "blue"),
	OCEANTROPICAL("Ocean tropical", "aqua"),
	BANQUISE("Banquise", "cyan"),
	PLAGE("Plage", "yellow"),
	PLAGEGLACE("Plage glacée", "yellow"),
	PLAGETROPICALE("Plage tropicale", "yellow"),
	MARAIS("Marais", "brown"),
	MARAISGLACE("Marais glacé", "brown"),
	MARAISTROPICAL("Marais tropical", "brown"),
	INLANDSIS("Inlandsis", "floralwhite"),
	FORET("Forêt", "forestgreen"),
	MANGROVE("Mangrove", "firebrick"),
	PERMAFROST("Permafrost", "deepskyblue"),
	FORETTROPICALE("Forêt tropicale", "green"),
	JUNGLE("Jungle", "green"),
	TOUNDRA("Toundra", "green"),
	TAIGA("Taiga", "green"),
	PLAINE("Plaine", "green"),
	SAVANE("Savane", "yellow"),
	DESERTGLACE("Désert glacé", "floralwhite"),
	STEPPE("Steppe", "gold"),
	PAMPA("Pampa", "goldrod"),
	DESERT("Desert", "yellow"),
	HAUTPLATEAU("Haut plateau", "red"),
	HAUTEFORETENNEIGEE("Haute forêt enneigée", "gray"),
	PLATEAUENNEIGEE("Plateau enneigé", "gray"),
	PLATEAUROCHEUXENNEIGE("Plateau rocheux enneigé", "gray"),
	HAUTEJUNGLE("Haute jungle", "lightgren"),
	PLATEAUROCHEUX("Plateau rocheux", "lightyellow"),
	HAUTEFORET("Haute forêt", "lime"),
	PLATEAUVOLVANIQUE("Plateau volcanique", "limegreen"),
	MONTAGNE("Montagne", "linen"),
	MONTAGNEFORETENNEIGEE("Montagne forêt enneigée", "magenta"),
	MONTAGNEENNEIGEE("Montagne enneigée", "maroon"),
	MONTAGNEROCHEUSEENNEIGEE("Montagne rocheuse enneigée", "mediumaquamarine"),
	MONTAGNEJUNGLE("Montagne jungle", "mediumblue"),
	MONTAGNEROCHEUSE("Montagne rocheuse", "mediumorchid"),
	MONTAGNEFORET("Montagne forêt", "mediumpurple"),
	VOLCAN("Volcan", "mediumseagreen"),
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
