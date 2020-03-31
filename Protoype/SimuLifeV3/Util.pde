//Liste des biomes
enum biomes {
  OCEAN("Ocean", 0, 0, 255, 10),
  OCEANTROPICAL("Ocean Tropical", 38, 196, 236, 9),
  BANQUISE("Banquise", 121, 248, 248, 8),
  PLAGE("Plage", 255, 255, 150, 6),
  PLAGEGLACE("Plage glacée", 244, 254, 254, 7),
  PLAGETROPICALE("Plage Tropicale", 223, 115, 255, 6),
  MARAIS("Marais", 46, 0, 108, 1),
  MARAISGLACE("Marais glacé", 187, 210, 225, 3),
  MARAISTROPICAL("Marais Tropical", 254, 150, 160, 2),
  INLANDSIS("Inlandsis", 237, 0, 0, 7),
  FORET("Forêt", 34, 139, 34, 1),
  MANGROVE("Mangrove", 84, 249, 141, 4),
  PERMAFROST("Permafrost", 201, 160, 220, 7),
  FORETTROPICALE("Forêt tropicale", 1, 215, 88, 1),
  JUNGLE("Jungle", 20, 148, 20, 1),
  TOUNDRA("Toundra", 139, 119, 195, 2),
  TAIGA("Taiga", 23, 87, 50, 2),
  PLAINE("Plaine", 87, 213, 59, 3),
  SAVANE("Savane", 194, 247, 50, 4),
  DESERTGLACE("Désert Glacé", 254, 231, 240, 10),
  STEPPE("Steppe", 253, 108, 158, 4),
  PAMPA("Pampa", 108, 2, 119, 4),
  DESERT("Désert", 224, 205, 169, 10),
  HAUTPLATEAU("Haut plateau", 212, 115, 212, 8),
  HAUTEFORETENNEIGEE("Haute forêt enneigée", 248, 142, 85, 2),
  PLATEAUENNEIGEE("Plateau enneigé", 133, 109, 77, 4),
  PLATEAUROCHEUXENNEIGE("Plateau rocheux enneigé", 223, 109, 20, 5),
  HAUTEJUNGLE("Haute jungle", 240, 195, 0, 2),
  PLATEAUROCHEUX("Plateau rocheux", 38, 196, 236, 5),
  HAUTEFORET("Haute forêt", 86, 115, 154, 2),
  PLATEAUVOLVANIQUE("Plateau volcanique", 204, 204, 255, 7),
  MONTAGNE("Montagne", 4, 139, 154, 5),
  MONTAGNEFORETENNEIGEE("Montagne forêt enneigée", 253, 233, 224, 5),
  MONTAGNEENNEIGEE("Montagne enneigée", 37, 253, 233, 5),
  MONTAGNEROCHEUSEENNEIGEE("Montagne rocheuse enneigée", 210, 202, 236, 5),
  MONTAGNEJUNGLE("Montagne jungle", 75, 0, 130, 2),
  MONTAGNEROCHEUSE("Montagne rocheuse", 218, 112, 214, 5),
  MONTAGNEFORET("Montagne forêt", 151, 223, 198, 2),
  VOLCAN("Volcan", 254, 27, 0, 10),
  ;
  
  private String name;
  private int r;
  private int g;
  private int b;
  private int ray;
  
  public int getR(){
    return this.r;
  }
  
  public int getG(){
    return this.g;
  }
  
  public int getB(){
    return this.b;
  }
  
  public int getRay(){
    return this.ray;
  }
  
  public String getName(){
    return this.name;
  }
  
  private biomes(final String name, final int r, final int g, final int b, final int ray){
    this.name = name;
    this.r = r;
    this.g = g;
    this.b = b;
    this.ray = ray;
  }
}
