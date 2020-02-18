class Biome{
  
  SimuLifeV2.biomes biome; 
  
  public Biome(float niv, float hum, float tem){
    if (niv > 0.85){
      if(tem > 0.8){
        biome = biomes.VOLCAN;
      }else if(tem > 0.5){
        if(hum > 0.5){
          biome = biomes.MONTAGNEJUNGLE;
        }else if(hum > -0.5){
          biome = biomes.MONTAGNE;
        }else{
          biome = biomes.MONTAGNEROCHEUSE;
        }
      }else if(tem > -0.5){
        if(hum > 0.8){
          biome = biomes.MONTAGNEJUNGLE;
        }else if(hum > 0.5){
          biome = biomes.MONTAGNEFORET;
        }else if(hum > -0.5){
          biome = biomes.MONTAGNE;
        }else{
          biome = biomes.MONTAGNEROCHEUSE;
        }
      }else{
        if(hum > 0.5){
          biome = biomes.MONTAGNEFORETENNEIGEE;
        }else if(hum > -0.8){
          biome = biomes.MONTAGNEENNEIGEE;
        }else{
          biome = biomes.MONTAGNEROCHEUSEENNEIGEE;
        }
      }
    }else if (niv > 0.55){
      if(tem > 0.8){
        biome = biomes.PLATEAUVOLVANIQUE;
      }else if(tem > 0.5){
        if(hum > 0.5){
          biome = biomes.HAUTEJUNGLE;
        }else if(hum > -0.5){
          biome = biomes.HAUTPLATEAU;
        }else{
          biome = biomes.PLATEAUROCHEUX;
        }
      }else if(tem > -0.5){
        if(hum > 0.8){
          biome = biomes.HAUTEJUNGLE;
        }else if(hum > 0.5){
          biome = biomes.HAUTEFORET;
        }else if(hum > -0.5){
          biome = biomes.HAUTPLATEAU;
        }else{
          biome = biomes.PLATEAUROCHEUX;
        }
      }else{
        if(hum > 0.5){
          biome = biomes.HAUTEFORETENNEIGEE;
        }else if(hum > -0.8){
          biome = biomes.PLATEAUENNEIGEE;
        }else{
          biome = biomes.PLATEAUROCHEUXENNEIGE;
        }
      }
    }else if (niv > -0.05){
      if(hum > 0.8){
        if(tem > 0.5){
          biome = biomes.MANGROVE;
        }else if (tem > -0.5){
          biome = biomes.FORET;
        }else{
          biome = biomes.INLANDSIS;
        }
      }else if (hum > 0.5){
        if(tem > 0.8){
          biome = biomes.JUNGLE;
        }else if (tem > 0.5){
          biome = biomes.FORETTROPICALE;
        }else if (tem > -0.5){
          biome = biomes.FORET;
        }else{
          biome = biomes.PERMAFROST;
        }
      }else if (hum > -0.5){
        if (tem > 0.5){
          biome = biomes.SAVANE;
        }else if (tem > -0.5){
          biome = biomes.PLAINE;
        }else if (tem > -0.8){
          biome = biomes.TAIGA;
        }else{
          biome = biomes.TOUNDRA;
        }
      }else if (hum > -0.8){
        if(tem > 0.8){
          biome = biomes.DESERT;
        }else if (tem > 0.5){
          biome = biomes.PAMPA;
        }else if (tem > -0.5){
          biome = biomes.PLAINE;
        }else if (tem > -0.8){
          biome = biomes.STEPPE;
        }else{
          biome = biomes.DESERTGLACE;
        }
      }else{
        if (tem > -0.5){
          biome = biomes.DESERT;
        }else{
          biome = biomes.DESERTGLACE;
        }
      }
    }else if (niv > -0.25){
      if(hum > 0.34){
        if (tem > 0.5){
          biome = biomes.MARAISTROPICAL;
        }else if (tem > -0.5) {
          biome = biomes.MARAIS;
        }else {
          biome = biomes.MARAISGLACE;
        }
      }else {
        if (tem > 0.5){
          biome = biomes.PLAGETROPICALE;
        }else if (tem > -0.5) {
          biome = biomes.PLAGE;
        }else {
          biome = biomes.PLAGEGLACE;
        }
      }
    }else{
      if (tem > 0.5){
        biome = biomes.OCEANTROPICAL;
      }else if (tem > -0.5) {
        biome = biomes.OCEAN;
      }else {
        biome = biomes.BANQUISE;
      }
    }
  }
  
  public SimuLifeV2.biomes getBiome(){
    return biome;
  }
  
  public color getColor(){
    return color(biome.getR(), biome.getG(), biome.getB());
  }
  
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Biome b = (Biome) o;
      return getBiome() == b.getBiome();
  }

  @Override
  public int hashCode() {
      return Objects.hash(getBiome());
  }
}
