import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashSet; 
import java.util.Objects; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GenObj extends PApplet {



HashSet<Cell> grid = new HashSet<Cell>();

Cell current;
Cell next;

int nbX, nbY;
int scl = 7;
int cols, rows;
float freq = 0.2f;
float inc = 0.05f;
int seed;

//déplacement
boolean depg = false;
boolean depd = false;
boolean deph = false;
boolean depb = false;
int origX = 0;
int origY = 0;

public void setup(){
  //size(600, 600);
  
  cols = width/scl;
  rows = height/scl;
  seed = round(random(99999999));
  noiseSeed(seed);
}

public void draw(){
  initMap();
  //plateau
  for(Cell c : grid){
    float ox = (c.posX - origX) * sqrt(3) * scl + (c.posY - origY) % 2 * sqrt(3) * scl / 2;
    float oy = (c.posY - origY) * 3 * scl / 2;
    c.draw(ox, oy, scl);
    if(c.isOver(mouseX, mouseY, ox, oy, scl)){
      next = c;
    }
  }
  
  //case selectionne
  if(current != null){
    strokeWeight(3);
    stroke(255, 0, 0);
    beginShape();
    noFill();
    float angle = TWO_PI / 6;
    float cx = (current.posX - origX) * sqrt(3) * scl + (current.posY - origY) % 2 * sqrt(3) * scl / 2;
    float cy = (current.posY - origY) * 3 * scl / 2;
    for(float a = PI / 6; a < TWO_PI; a += angle){
      float hx = cx + cos(a) * scl;
      float hy = cy + sin(a) * scl;
      vertex(hx, hy);
    }
    endShape(CLOSE);
    strokeWeight(1);
  }
  
  //infos
  stroke(0);
  fill(255);
  rect(0, 0, 150, 35);
  rect(0, 35, 150, 35);
  textSize(26);
  fill(0);
  if(current != null){
    text(current.getBiome(), 10, 25);
  }
  text(grid.size(), 5, 60);
}

public void initView(){
  nbX = width / scl;
  nbY = height / scl;
}

public void initMap(){
  //grid.clear();
  for(int y = 0; y < rows; y ++){
    for (int x = 0; x < cols; x ++){
      float niv = noise(x / 20.0f, y / 20.0f);
      float hum = noise(freq * x / 20.0f, freq * y / 20.0f);
      grid.add(new Cell(x, y, getBiome(niv,hum)));
    }
  }
}

public void mousePressed(){
  if(current == next){
    current = null;
  }else{
    current = next;
  }
}

public void keyPressed(){
  switch(key){
    case '+':
      scl += 10;
      initView();
      break;
    case '-':
      scl = scl > 10 ? scl - 10 : 10;
      initView();
      break;
    case 'z':
      origY += 2;
      break;
    case 'q':
      origX -= 2;
      break;
    case 's':
      origY -= 2;
      break;
    case 'd':
      origX += 2;
      break;
  }
}

public void keyReleased(){
  switch(key){
    case 'z':
      origY += 2;
      break;
    case 'q':
      origX -= 2;
      break;
    case 's':
      origY -= 2;
      break;
    case 'd':
      origX += 2;
      break;
  }
}


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

public int getCol(GenObj.biomes biome){
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



public GenObj.biomes getBiome(float niv, float hum){
  if (niv < 0.35f){
    return biomes.OCEAN;
  }
  //if (niv < 0.375){
  //  return biomes.PLAGE;
  //}
  
  if (niv > 0.75f) {
    if (hum < 0.3f){ 
      return biomes.MONTAGNE;
    }
    if (hum < 0.8f){
      return biomes.TUNDRA;
    }
    return biomes.NEIGE;
  }

  if (niv > 0.65f) {
    if (hum < 0.33f){
      return biomes.DESERT;
    }
    if (hum < 0.66f){
      return biomes.TAIGA;
    }
    return biomes.MARECAGE;
  }

  if (niv > 0.45f) {
    if (hum < 0.16f){
      return biomes.DESERT;
    }
    if (hum < 0.50f){
      return biomes.PLAINE;
    }
    if (hum < 0.83f){
      return biomes.FORET;
    }
    return biomes.JUNGLE;
  }
  return biomes.PLAINE;
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GenObj" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
