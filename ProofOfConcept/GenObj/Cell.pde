import java.util.Objects;

class Cell{
  
  GenObj.biomes biome;
  int posX, posY;
  
  public Cell(int x, int y, GenObj.biomes biome){
    posX = x;
    posY = y;
    this.biome = biome;
  }
  
  public void draw(float x, float y, int size){
    
    float angle = TWO_PI / 6;
    translate(x, y);
    beginShape();
    noStroke();
    fill(getCol(this.biome));
    for(float a = PI / 6; a < TWO_PI; a += angle){
      float hx = cos(a) * size;
      float hy = sin(a) * size;
      vertex(hx, hy);
    }
    endShape(CLOSE);
    translate(-x, -y);
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
  
  public boolean isOver(float mx, float my, float ox, float oy, int size){
    if(pow(mx-ox, 2) + pow(my-oy, 2) <= pow(size * sqrt(3) / 2, 2)){
      return true;
    }
    return false;
  }
  
  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return posX == cell.posX &&
                posY == cell.posY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }
  
}
