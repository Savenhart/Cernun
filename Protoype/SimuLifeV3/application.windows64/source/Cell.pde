class Cell{
  
  Biome biome;
  
  public Cell(float niv, float hum, float tem){
    biome = new Biome(niv, hum, tem);
  }
  
  public Biome getBiome(){
    return biome;
  }
  
  
  
// ==============
// Partie visuelle
// ==============
  public void draw(float x, float y, float size){
    float angle = TWO_PI / 6;
    translate(x, y);
    beginShape();
    noStroke();
    fill(this.biome.getColor());
    for(float a = 0; a < TWO_PI; a += angle){
      float hx = cos(a) * (size + 1);
      float hy = sin(a) * (size + 1);
      vertex(hx, hy);
    }
    endShape(CLOSE);
    translate(-x, -y);
  }
}
