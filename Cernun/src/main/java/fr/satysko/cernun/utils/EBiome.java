package fr.satysko.cernun.utils;

public enum EBiome {
	OCEAN("Ocean", new String[] {"blue"}, 10),
	OCEANTROPICAL("Ocean tropical", new String[] {"aqua"}, 9),
	BANQUISE("Banquise", new String[] {"cyan"}, 8),
	PLAGE("Plage", new String[] {"yellow"}, 6),
	PLAGEGLACE("Plage glacée", new String[] {"yellow"}, 7),
	PLAGETROPICALE("Plage tropicale", new String[] {"yellow"}, 6),
	MARAIS("Marais", new String[] {"brown"}, 1),
	MARAISGLACE("Marais glacé", new String[] {"brown"}, 3),
	MARAISTROPICAL("Marais tropical", new String[] {"brown"},2),
	INLANDSIS("Inlandsis", new String[] {"floralwhite"}, 7),
	FORET("Forêt", new String[] {"forestgreen"}, 1),
	MANGROVE("Mangrove", new String[] {"firebrick"}, 4),
	PERMAFROST("Permafrost", new String[] {"deepskyblue"}, 7),
	FORETTROPICALE("Forêt tropicale", new String[] {"green"}, 1),
	JUNGLE("Jungle", new String[] {"green"}, 1),
	TOUNDRA("Toundra", new String[] {"green"},2),
	TAIGA("Taiga", new String[] {"green"}, 2),
	PLAINE("Plaine", new String[] {"green"}, 3),
	SAVANE("Savane", new String[] {"yellow"}, 4),
	DESERTGLACE("Désert glacé", new String[] {"floralwhite"}, 10),
	STEPPE("Steppe", new String[] {"gold"}, 4),
	PAMPA("Pampa", new String[] {"goldrod"}, 4),
	DESERT("Desert", new String[] {"yellow"}, 10),
	HAUTPLATEAU("Haut plateau", new String[] {"red"}, 8),
	HAUTEFORETENNEIGEE("Haute forêt enneigée", new String[] {"gray"}, 2),
	PLATEAUENNEIGEE("Plateau enneigé", new String[] {"gray"}, 4),
	PLATEAUROCHEUXENNEIGE("Plateau rocheux enneigé", new String[] {"gray"}, 5),
	HAUTEJUNGLE("Haute jungle", new String[] {"lightgren"}, 2),
	PLATEAUROCHEUX("Plateau rocheux", new String[] {"lightyellow"}, 5),
	HAUTEFORET("Haute forêt", new String[] {"lime"}, 2),
	PLATEAUVOLVANIQUE("Plateau volcanique", new String[] {"limegreen"}, 7),
	MONTAGNE("Montagne", new String[] {"linen"}, 5),
	MONTAGNEFORETENNEIGEE("Montagne forêt enneigée", new String[] {"magenta"}, 5),
	MONTAGNEENNEIGEE("Montagne enneigée", new String[] {"maroon"}, 5),
	MONTAGNEROCHEUSEENNEIGEE("Montagne rocheuse enneigée", new String[] {"mediumaquamarine"}, 5),
	MONTAGNEJUNGLE("Montagne jungle", new String[] {"mediumblue"}, 2),
	MONTAGNEROCHEUSE("Montagne rocheuse", new String[] {"mediumorchid"}, 5),
	MONTAGNEFORET("Montagne forêt", new String[] {"mediumpurple"}, 2),
	VOLCAN("Volcan", new String[] {"mediumseagreen"}, 10),
	;

	private String name;
	private String[] path;
	private int rank;

	public String getName(){
		return this.name;
	}

	public String[] getPath(){
		return this.path;
	}

	public int getRank(){
		return this.rank;
	}

	EBiome(final String name, final String[] path, final int rank) {
		this.name = name;
		this.path = path;
		this.rank = rank;
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
