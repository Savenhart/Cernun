import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Objects; 
import java.util.HashMap; 
import java.util.Map; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SimuLife extends PApplet {

World monde;

Cell current;
Cell next;

int scl = 10;
int nbCell;

//déplacement
boolean depg = false;
boolean depd = false;
boolean deph = false;
boolean depb = false;
int origX = 0;
int origY = 0;

public void setup(){
  
  //fullScreen();
  
  nbCell = 10;
  
  //monde = new World("Monde 1", 46854);
  monde = new World("Monde 1", round(random(999999999)));
  
}

public void draw(){
  
  background(255);
  
  origX += ((depd? 1 : 0) - (depg ? 1 : 0)) * 2;
  origY += ((depb? 1 : 0) - (deph ? 1 : 0)) * 2;
  
  monde.draw(origX, origY, nbCell, (height / 2)/(2 * nbCell + 1), 2);//Hexagone
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
    case '-':
      nbCell = nbCell < 50 ? nbCell += 10 : 50;
      break;
    case '+':
      nbCell = nbCell > 10 ? nbCell - 10 : 10;
      break;
    case 'z':
      deph = true;
      break;
    case 'q':
      depg = true;
      break;
    case 's':
      depb = true;
      break;
    case 'd':
      depd = true;
      break;
    case 'p':
      monde.debug();
      break;
  }
  
}

public void keyReleased(){
  switch(key){
    case 'z':
      deph = false;
      break;
    case 'q':
      depg = false;
      break;
    case 's':
      depb = false;
      break;
    case 'd':
      depd = false;
      break;
  }
}
class Biome{
  
  SimuLife.biomes biome; 
  
  public Biome(float niv, float hum){
    biome = biomes.OCEAN;
    if (niv > - 0.20f){
      biome = biomes.PLAGE;
    }
    //if (niv < 0.375){
    //  return biomes.PLAGE;
    //}
  
    if (niv > -0.15f) {
      biome = biomes.JUNGLE;
      if (hum < 0.83f){
        biome = biomes.FORET;
      }
      if (hum < 0.50f){
        biome = biomes.PLAINE;
      }
      if (hum < 0.16f){
        biome = biomes.DESERT;
      }
    }
  
    if (niv > 0.65f) {
      biome = biomes.MARECAGE;
      if (hum < 0.66f){
        biome = biomes.TAIGA;
      }
      if (hum < 0.33f){
        biome = biomes.DESERT;
      }
    }
    
    if (niv > 0.75f) {
      biome = biomes.NEIGE;
      if (hum < 0.8f){
        biome = biomes.TUNDRA;
      }
      if (hum < 0.3f){ 
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
  
  public int getColor(){
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
    for(float a = 0; a < TWO_PI; a += angle){
      float hx = cos(a) * (size + 1);
      float hy = sin(a) * (size + 1);
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


class Key{
  
  int posX, posY;
  
  public Key(int x, int y){
    posX = x;
    posY = y;
  }
  
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Key k = (Key) o;
      return posX == k.posX &&
              posY == k.posY;
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



class World{
  
  Map<Key, Cell> world;
  String name;
  long seed;
  int id;
  
  OpenSimplexNoise oNoise;
  
  public World(String n, long s){
    name = n;
    seed = s;
    oNoise = new OpenSimplexNoise(seed);
    world = new HashMap<Key, Cell>();
  }
  
  public void genCell(int x, int y){
    Key k = new Key(x, y);
    float niv = (float) oNoise.eval((x + 00) / 20.0f, (y + 00) / 20.0f);
    float hum = (float) oNoise.eval(0.2f * (x + 00) / 20.0f, 0.2f * (y + 00) / 20.0f);
    Cell c = new Cell(niv, hum);
    world.put(k, c);
  }
  
  public void draw(int x, int y, int nb, int size, int forme){
    float cx = x * size * 3 / 2;
    float cy = y * sqrt(3) * size + x % 2 * sqrt(3) * size / 2;
    if(forme == 1){
      for(int i = x - nb; i <= x + nb; i++){
        for(int j = y - nb; j <= y + nb; j++){
          Key k = new Key(i, j);
          if(!world.containsKey(k)){
            genCell(i, j);
          }
          Cell c = world.get(k);
          translate(width / 2 - cx, height / 2 - cy);
          c.draw(i * 3 * size / 2, j * sqrt(3) * size + abs(i) % 2 * sqrt(3) * size / 2, size);
          translate(-width / 2 + cx, - height / 2 + cy);
        }
      }
    }else{
      for(int py = -nb; py <= nb; py++){
        for(int px = -nb; px <= nb; px++){
          // rectangle centrale
          if(abs(py) < nb / 2
          // Triangle inférieur
          || (py > 0 && (
          // Pour les colonnes impaires, ne prends pas en compte la dernière ligne courante
                (px % 2 != 0 && py < nb - abs(px) / 2)
          // Pour les colonnes paires, prends en compte la dernière ligne courante
             || (px % 2 == 0 && py <= nb - abs(px) / 2)))
          // Triangle supérieur
          || (py < 0 && (
          // Pour les colonnes impaires, ne prends pas en compte la dernière ligne suivante
                (px % 2 != 0 && -py < nb - abs(px) / 2 + 1)
          // Pour les colonnes paires, prends en compte la dernière ligne courante
             || (px % 2 == 0 && -py <= nb - abs(px) / 2)))
          ){
            Key k = new Key(x + px, y + py);
            if(!world.containsKey(k)){
                genCell(x + px, y + py);
              }
            Cell c = world.get(k);
            translate(width / 2 - cx, height / 2 - cy);
            c.draw((x + px) * 3 * size / 2, (y + py) * sqrt(3) * size + abs(x + px) % 2 * sqrt(3) * size / 2, size);
            fill(0);
            translate(-width / 2 + cx, - height / 2 + cy);
          }
        }
      }
    }
  }
  
  public void debug(){
    for(Map.Entry<Key , Cell> entry : world.entrySet()){
      System.out.println(entry.getKey().posX + " " + entry.getKey().posY + " | " + entry.getValue().getBiome());
    }
  }
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SimuLife" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
