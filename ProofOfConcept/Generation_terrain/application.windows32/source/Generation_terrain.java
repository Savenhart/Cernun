import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Generation_terrain extends PApplet {

float inc = 0.05f;
int scl = 10;
int cols, rows;
float[][] niveau;
float[][] humidite;

int centX, centY, nbX, nbY;

String currentB = "";

float freq = 0.2f;


public void setup(){
  //size(600, 600);
  
  cols = width/scl;
  rows = height/scl;
  niveau = new float[cols][rows];
  humidite = new float[cols][rows];
  initMap();
  
  centX = width / 2;
  centY = height / 2;
  initView();
}

public void draw(){
  
  int posX = centX * cols / width;
  int posY = centY * rows / height;
  
  for(int y = 0; y < rows; y ++){
    for (int x = 0; x < cols; x ++){
      drawCell(x, y);
    }
  }
  
  //fill(255);
  //stroke(0);
  //rect(0, 0, 150, 35);
  
  //fill(0);
  //textSize(26);
  //text(biomeTxt(), 10, 25);
  
}

public void initView(){
  nbX = width / scl;
  nbY = height / scl;
}

public void initMap(){
  //noiseSeed(0);
  float yoff = 0.0f;
  for(int y = 0; y < rows; y ++){
    float xoff = 0.0f;
    for (int x = 0; x < cols; x ++){
      float niv = noise(xoff, yoff);
      float hum = noise(freq * xoff, freq * yoff);
      niveau[x][y] = niv;
      humidite[x][y] = hum;
      
      xoff += inc;
    }
    yoff += inc;
  }
}

public void drawCell(int x, int y){
  float niv = niveau[x][y];
  float hum = humidite[x][y];
  noStroke();
  fill(getCol(niv, hum));
  drawHexa(x, y);
  //rect(x * scl, y * scl, scl, scl);
}

public void drawHexa(float x, float y){
  
  float ox = x * sqrt(3) * scl + y % 2 * sqrt(3) * scl / 2;
  float oy = y * 3 * scl / 2;
  
  float angle = TWO_PI / 6;
  translate(ox, oy);
  beginShape();
  for(float a = PI / 6; a < TWO_PI; a += angle){
    float hx = cos(a) * scl;
    float hy = sin(a) * scl;
    vertex(hx, hy);
  }
  endShape(CLOSE);
  translate(-ox, -oy);
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
      centY -= 5;
      break;
    case 'q':
      centX -= 5;
      break;
    case 's':
      centY += 5;
      break;
    case 'd':
      centX += 5;
      break;
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

public int getCol(float niv, float hum){
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
  if (niv < 0.35f){
    return biomes.OCEAN;
  }
  if (niv < 0.375f){
    return biomes.PLAGE;
  }
  
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
      return biomes.MARECAGE;
    }
    return biomes.TAIGA;
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
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Generation_terrain" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
