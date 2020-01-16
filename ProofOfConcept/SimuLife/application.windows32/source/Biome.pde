class Biome{
  
  SimuLife.biomes biome; 
  
  public Biome(float niv, float hum){
    //System.out.println(niv + " " + hum);
    biome = biomes.OCEAN;
    if (niv > 0.35){
      biome = biomes.PLAGE;
    }
    //if (niv < 0.375){
    //  return biomes.PLAGE;
    //}
  
    if (niv > 0.45) {
      biome = biomes.JUNGLE;
      if (hum < 0.83){
        biome = biomes.FORET;
      }
      if (hum < 0.50){
        biome = biomes.PLAINE;
      }
      if (hum < 0.16){
        biome = biomes.DESERT;
      }
    }
  
    if (niv > 0.65) {
      biome = biomes.MARECAGE;
      if (hum < 0.66){
        biome = biomes.TAIGA;
      }
      if (hum < 0.33){
        biome = biomes.DESERT;
      }
    }
    
    if (niv > 0.75) {
      biome = biomes.NEIGE;
      if (hum < 0.8){
        biome = biomes.TUNDRA;
      }
      if (hum < 0.3){ 
        biome = biomes.MONTAGNE;
      }
    }
  }
  
  public String getBiome(){
    switch(biome){
      case OCEAN:
        return "Ocean";
      case PLAGE:
        return "Plage";
      case PLAINE:
        return "Plaine";
      case MONTAGNE:
        return "Montagne";
      case TUNDRA:
        return "Tundra";
      case NEIGE:
        return "Neige";
      case DESERT:
        return "Desert";
      case FORET:
        return "Foret";
      case TAIGA:
        return "Taiga";
      case MARECAGE:
        return "Marecage";
      case JUNGLE:
        return "Jungle";
    }
    return "";
  }
  
  public color getColor(){
    int r = 0;
    int g = 0;
    int b = 0;
    
    switch(biome){
      case OCEAN:
        b = 255;
        break;
      case PLAGE:
        r = 255;
        g = 255;
        b = 150;
        break;
      case PLAINE:
        r = 87;
        g = 213;
        b = 59;
        break;
      case MONTAGNE:
        r = 128;
        break;
      case TUNDRA:
        r = 139;
        g = 119;
        b = 195;
        break;
      case NEIGE:
        r = 255;
        g = 255;
        b = 255;
        break;
      case DESERT:
        r = 255;
        g = 255;
        break;
      case FORET:
        r = 34;
        g = 139;
        b = 34;
        break;
      case TAIGA:
        r = 23;
        g = 87;
        b = 50;
        break;
      case MARECAGE:
        r = 46;
        b = 108;
        break;
      case JUNGLE:
        r = 20;
        g = 148;
        b = 20;
        break;
    }
    
    return color(r, g, b);
  }
  
}
