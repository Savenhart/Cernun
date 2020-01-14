class Cell{
  
  Biome biome;
  
  public Cell(float niv, float hum){
    biome = new Biome(niv, hum);
  }
  
  public String getBiome(){
    return biome.getBiome();
  }
  
  public void draw(float x, float y, int size){
    float angle = TWO_PI / 6;
    translate(x, y);
    beginShape();
    noStroke();
    fill(this.biome.getColor());
    for(float a = PI / 6; a < TWO_PI; a += angle){
      float hx = cos(a) * size;
      float hy = sin(a) * size;
      vertex(hx, hy);
    }
    endShape(CLOSE);
    translate(-x, -y);
  }
  
  public boolean isOver(float mx, float my, float ox, float oy, int size){
    if(pow(mx-ox, 2) + pow(my-oy, 2) <= pow(size * sqrt(3) / 2, 2)){
      return true;
    }
    return false;
  }
}
