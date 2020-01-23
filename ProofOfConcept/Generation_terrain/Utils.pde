enum biomes {
  OCEAN,
  PLAGE,
  PLAINE,
  MONTAGNE,
  TUNDRA,
  NEIGE,
  DESERT,
  FORET,
  TAIGA,
  MARECAGE,
  JUNGLE
}

public color getCol(float niv, float hum){
  int r = 0;
  int g = 0;
  int b = 0;
  
  switch(getBiome(niv, hum)){
    case OCEAN:
      b = 255;
      break;
    case PLAGE:
      r = 255;
      g = 255;
      b = 100;
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
      g = 0;
      b = 0;
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



public Generation_terrain.biomes getBiome(float niv, float hum){
  if (niv < 0.35){
    return biomes.OCEAN;
  }
  if (niv < 0.375){
    return biomes.PLAGE;
  }
  
  if (niv > 0.75) {
    if (hum < 0.3){ 
      return biomes.MONTAGNE;
    }
    if (hum < 0.8){
      return biomes.TUNDRA;
    }
    return biomes.NEIGE;
  }

  if (niv > 0.65) {
    if (hum < 0.33){
      return biomes.DESERT;
    }
    if (hum < 0.66){
      return biomes.MARECAGE;
    }
    return biomes.TAIGA;
  }

  if (niv > 0.45) {
    if (hum < 0.16){
      return biomes.DESERT;
    }
    if (hum < 0.50){
      return biomes.PLAINE;
    }
    if (hum < 0.83){
      return biomes.FORET;
    }
    return biomes.JUNGLE;
  }
  return biomes.PLAINE;
}

public String biomeTxt(){
  int x = mouseX / scl;
  int y = mouseY / scl;
  switch(getBiome(niveau[x][y], humidite[x][y])){
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
