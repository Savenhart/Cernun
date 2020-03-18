package fr.satysko.cernun.utils;

public enum EBiome {
	OCEAN("Ocean", new String[] {"blue"}),
	OCEANTROPICAL("Ocean tropical", new String[] {"aqua"}),
	BANQUISE("Banquise", new String[] {"cyan"}),
	PLAGE("Plage", new String[] {"yellow"}),
	PLAGEGLACE("Plage glacée", new String[] {"yellow"}),
	PLAGETROPICALE("Plage tropicale", new String[] {"yellow"}),
	MARAIS("Marais", new String[] {"brown"}),
	MARAISGLACE("Marais glacé", new String[] {"brown"}),
	MARAISTROPICAL("Marais tropical", new String[] {"brown"}),
	INLANDSIS("Inlandsis", new String[] {"floralwhite"}),
	FORET("Forêt", new String[] {"forestgreen"}),
	MANGROVE("Mangrove", new String[] {"firebrick"}),
	PERMAFROST("Permafrost", new String[] {"deepskyblue"}),
	FORETTROPICALE("Forêt tropicale", new String[] {"green"}),
	JUNGLE("Jungle", new String[] {"green"}),
	TOUNDRA("Toundra", new String[] {"green"}),
	TAIGA("Taiga", new String[] {"green"}),
	PLAINE("Plaine", new String[] {"green"}),
	SAVANE("Savane", new String[] {"yellow"}),
	DESERTGLACE("Désert glacé", new String[] {"floralwhite"}),
	STEPPE("Steppe", new String[] {"gold"}),
	PAMPA("Pampa", new String[] {"goldrod"}),
	DESERT("Desert", new String[] {"yellow"}),
	HAUTPLATEAU("Haut plateau", new String[] {"red"}),
	HAUTEFORETENNEIGEE("Haute forêt enneigée", new String[] {"gray"}),
	PLATEAUENNEIGEE("Plateau enneigé", new String[] {"gray"}),
	PLATEAUROCHEUXENNEIGE("Plateau rocheux enneigé", new String[] {"gray"}),
	HAUTEJUNGLE("Haute jungle", new String[] {"lightgren"}),
	PLATEAUROCHEUX("Plateau rocheux", new String[] {"lightyellow"}),
	HAUTEFORET("Haute forêt", new String[] {"lime"}),
	PLATEAUVOLVANIQUE("Plateau volcanique", new String[] {"limegreen"}),
	MONTAGNE("Montagne", new String[] {"linen"}),
	MONTAGNEFORETENNEIGEE("Montagne forêt enneigée", new String[] {"magenta"}),
	MONTAGNEENNEIGEE("Montagne enneigée", new String[] {"maroon"}),
	MONTAGNEROCHEUSEENNEIGEE("Montagne rocheuse enneigée", new String[] {"mediumaquamarine"}),
	MONTAGNEJUNGLE("Montagne jungle", new String[] {"mediumblue"}),
	MONTAGNEROCHEUSE("Montagne rocheuse", new String[] {"mediumorchid"}),
	MONTAGNEFORET("Montagne forêt", new String[] {"mediumpurple"}),
	VOLCAN("Volcan", new String[] {"mediumseagreen"}),
	;

	private String name;
	private String[] path;

	public String getName(){
		return this.name;
	}

	public String[] getPath(){
		return this.path;
	}

	EBiome(final String name, final String[] path) {
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
