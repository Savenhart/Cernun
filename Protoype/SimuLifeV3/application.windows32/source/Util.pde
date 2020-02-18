//Liste des biomes
enum biomes {
  OCEAN("Ocean", 0, 0, 255),
  OCEANTROPICAL("Ocean Tropical", 38, 196, 236),
  BANQUISE("Banquise", 121, 248, 248),
  PLAGE("Plage", 255, 255, 150),
  PLAGEGLACE("Plage glacée", 244, 254, 254),
  PLAGETROPICALE("Plage Tropicale", 223, 115, 255),
  MARAIS("Marais", 46, 0, 108),
  MARAISGLACE("Marais glacé", 187, 210, 225),
  MARAISTROPICAL("Marais Tropical", 254, 150, 160),
  INLANDSIS("Inlandsis", 237, 0, 0),
  FORET("Forêt", 34, 139, 34),
  MANGROVE("Mangrove", 84, 249, 141),
  PERMAFROST("Permafrost", 201, 160, 220),
  FORETTROPICALE("Forêt tropicale", 1, 215, 88),
  JUNGLE("Jungle", 20, 148, 20),
  TOUNDRA("Toundra", 139, 119, 195),
  TAIGA("Taiga", 23, 87, 50),
  PLAINE("Plaine", 87, 213, 59),
  SAVANE("Savane", 194, 247, 50),
  DESERTGLACE("Désert Glacé", 254, 231, 240),
  STEPPE("Steppe", 253, 108, 158),
  PAMPA("Pampa", 108, 2, 119),
  DESERT("Désert", 224, 205, 169),
  HAUTPLATEAU("Haut plateau", 212, 115, 212),
  HAUTEFORETENNEIGEE("Haute forêt enneigée", 248, 142, 85),
  PLATEAUENNEIGEE("Plateau enneigé", 133, 109, 77),
  PLATEAUROCHEUXENNEIGE("Plateau rocheux enneigé", 223, 109, 20),
  HAUTEJUNGLE("Haute jungle", 240, 195, 0),
  PLATEAUROCHEUX("Plateau rocheux", 38, 196, 236),
  HAUTEFORET("Haute forêt", 86, 115, 154),
  PLATEAUVOLVANIQUE("Plateau volcanique", 204, 204, 255),
  MONTAGNE("Montagne", 4, 139, 154),
  MONTAGNEFORETENNEIGEE("Montagne forêt enneigée", 253, 233, 224),
  MONTAGNEENNEIGEE("Montagne enneigée", 37, 253, 233),
  MONTAGNEROCHEUSEENNEIGEE("Montagne rocheuse enneigée", 210, 202, 236),
  MONTAGNEJUNGLE("Montagne jungle", 75, 0, 130),
  MONTAGNEROCHEUSE("Montagne rocheuse", 218, 112, 214),
  MONTAGNEFORET("Montagne forêt", 151, 223, 198),
  VOLCAN("Volcan", 254, 27, 0),
  ;
  
  private String name;
  private int r;
  private int g;
  private int b;
  
  public int getR(){
    return this.r;
  }
  
  public int getG(){
    return this.g;
  }
  
  public int getB(){
    return this.b;
  }
  
  public String getName(){
    return this.name;
  }
  
  private biomes(final String name, final int r, final int g, final int b){
    this.name = name;
    this.r = r;
    this.g = g;
    this.b = b;
  }
}
