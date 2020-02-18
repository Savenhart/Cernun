import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Set; 
import java.util.Objects; 
import java.util.HashMap; 
import java.util.Map; 
import java.util.HashSet; 
import java.util.Set; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SimuLifeV2 extends PApplet {





World monde;

Cell current;

//zoom
int min = 5;
int max = 55;
int nbZoom = 5;
int pas;
float scl;
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
  
  //initialisation zoom
  pas = (max - min) / nbZoom;
  nbCell = min + pas;
  
  //Initialisation monde
  monde = new World("Monde 1", 46854);
  //monde = new World("Monde 1", round(random(999999999)));
  
}


// ==============
// Partie visuelle
// ==============

public void draw(){
  
  background(255);
  
  //gestion déplacement au clavier
  if(depd || depg || depb || deph){
    origX += ((depd? 1 : 0) - (depg ? 1 : 0)) * 2;
    origY += ((depb? 1 : 0) - (deph ? 1 : 0)) * 2;
  }
  
  //calcul de la taille des cellules et affichage
  scl = (height / 2)/(2.0f * nbCell + 1);
  Set<Biome> legende = monde.draw(origX, origY, nbCell, scl);
  
  //Affichage de la légende
  stroke(0);
  line(height, 0, width, 0);
  line(width - 1, 0, width - 1, height);
  line(width - 1, height - 1, height, height - 1);
  line(height, 0, height, height);
  int index = 1;
  for(Biome b : legende){
    translate(height + 50, 30 * index);
    float angle = TWO_PI / 6;
    beginShape();
    fill(b.getColor());
    for(float a = 0; a < TWO_PI; a += angle){
      float hx = cos(a) * 10;
      float hy = sin(a) * 10;
      vertex(hx, hy);
    }
    endShape(CLOSE);
    fill(0);
    text(b.getBiome().getName(), 20, 0);
    translate(-height - 50, -30 * index);
    index++;
  }
}


//gestion de l'emplacement de la souris
public void mouseMoved(){
  fill(255, 0, 0);
  circle(mouseX, mouseY, 5);
}

//gestion déplacement
public void mouseDragged(){
  origX += ((pmouseX - mouseX > 0? 1 : 0) - (pmouseX - mouseX < 0 ? 1 : 0)) * 2;
  origY += ((pmouseY - mouseY > 0? 1 : 0) - (pmouseY - mouseY < 0 ? 1 : 0)) * 2;
}

//gestion zoom a la souris
public void mouseWheel(MouseEvent event){
  int e = event.getCount() < 0 ? 1 : -1;
  nbCell = e < 0? (nbCell < max ? nbCell += pas : max) : (nbCell = nbCell > min ? nbCell - pas : min);
}

public void keyPressed(){
  switch(key){
    case '-':
      nbCell = nbCell < max ? nbCell += pas : max;
      break;
    case '+':
      nbCell = nbCell > min ? nbCell - pas : min;
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
  
  SimuLifeV2.biomes biome; 
  
  public Biome(float niv, float hum, float tem){
    if (niv > 0.85f){
      if(tem > 0.8f){
        biome = biomes.VOLCAN;
      }else if(tem > 0.5f){
        if(hum > 0.5f){
          biome = biomes.MONTAGNEJUNGLE;
        }else if(hum > -0.5f){
          biome = biomes.MONTAGNE;
        }else{
          biome = biomes.MONTAGNEROCHEUSE;
        }
      }else if(tem > -0.5f){
        if(hum > 0.8f){
          biome = biomes.MONTAGNEJUNGLE;
        }else if(hum > 0.5f){
          biome = biomes.MONTAGNEFORET;
        }else if(hum > -0.5f){
          biome = biomes.MONTAGNE;
        }else{
          biome = biomes.MONTAGNEROCHEUSE;
        }
      }else{
        if(hum > 0.5f){
          biome = biomes.MONTAGNEFORETENNEIGEE;
        }else if(hum > -0.8f){
          biome = biomes.MONTAGNEENNEIGEE;
        }else{
          biome = biomes.MONTAGNEROCHEUSEENNEIGEE;
        }
      }
    }else if (niv > 0.55f){
      if(tem > 0.8f){
        biome = biomes.PLATEAUVOLVANIQUE;
      }else if(tem > 0.5f){
        if(hum > 0.5f){
          biome = biomes.HAUTEJUNGLE;
        }else if(hum > -0.5f){
          biome = biomes.HAUTPLATEAU;
        }else{
          biome = biomes.PLATEAUROCHEUX;
        }
      }else if(tem > -0.5f){
        if(hum > 0.8f){
          biome = biomes.HAUTEJUNGLE;
        }else if(hum > 0.5f){
          biome = biomes.HAUTEFORET;
        }else if(hum > -0.5f){
          biome = biomes.HAUTPLATEAU;
        }else{
          biome = biomes.PLATEAUROCHEUX;
        }
      }else{
        if(hum > 0.5f){
          biome = biomes.HAUTEFORETENNEIGEE;
        }else if(hum > -0.8f){
          biome = biomes.PLATEAUENNEIGEE;
        }else{
          biome = biomes.PLATEAUROCHEUXENNEIGE;
        }
      }
    }else if (niv > -0.05f){
      if(hum > 0.8f){
        if(tem > 0.5f){
          biome = biomes.MANGROVE;
        }else if (tem > -0.5f){
          biome = biomes.FORET;
        }else{
          biome = biomes.INLANDSIS;
        }
      }else if (hum > 0.5f){
        if(tem > 0.8f){
          biome = biomes.JUNGLE;
        }else if (tem > 0.5f){
          biome = biomes.FORETTROPICALE;
        }else if (tem > -0.5f){
          biome = biomes.FORET;
        }else{
          biome = biomes.PERMAFROST;
        }
      }else if (hum > -0.5f){
        if (tem > 0.5f){
          biome = biomes.SAVANE;
        }else if (tem > -0.5f){
          biome = biomes.PLAINE;
        }else if (tem > -0.8f){
          biome = biomes.TAIGA;
        }else{
          biome = biomes.TOUNDRA;
        }
      }else if (hum > -0.8f){
        if(tem > 0.8f){
          biome = biomes.DESERT;
        }else if (tem > 0.5f){
          biome = biomes.PAMPA;
        }else if (tem > -0.5f){
          biome = biomes.PLAINE;
        }else if (tem > -0.8f){
          biome = biomes.STEPPE;
        }else{
          biome = biomes.DESERTGLACE;
        }
      }else{
        if (tem > -0.5f){
          biome = biomes.DESERT;
        }else{
          biome = biomes.DESERTGLACE;
        }
      }
    }else if (niv > -0.25f){
      if(hum > 0.34f){
        if (tem > 0.5f){
          biome = biomes.MARAISTROPICAL;
        }else if (tem > -0.5f) {
          biome = biomes.MARAIS;
        }else {
          biome = biomes.MARAISGLACE;
        }
      }else {
        if (tem > 0.5f){
          biome = biomes.PLAGETROPICALE;
        }else if (tem > -0.5f) {
          biome = biomes.PLAGE;
        }else {
          biome = biomes.PLAGEGLACE;
        }
      }
    }else{
      if (tem > 0.5f){
        biome = biomes.OCEANTROPICAL;
      }else if (tem > -0.5f) {
        biome = biomes.OCEAN;
      }else {
        biome = biomes.BANQUISE;
      }
    }
  }
  
  public SimuLifeV2.biomes getBiome(){
    return biome;
  }
  
  public int getColor(){
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
//Liste des biomes
enum biomes {
  OCEAN("Ocean", 0, 0, 255),
  OCEANTROPICAL("Ocean Tropical", 38, 196, 236),
  BANQUISE("Banquise", 121, 248, 248),
  PLAGE("Plage", 255, 255, 150),
  PLAGEGLACE("Plage glacée", 244, 254, 254),
  PLAGETROPICALE("Plage Tropicale", 223, 115, 255),
  MARAIS("Marais", 46, 0, 108),
  MARAISGLACE("Marais glacé", 187, 210, 225),
  MARAISTROPICAL("Marais Tropical", 254, 150, 160),
  INLANDSIS("Inlandsis", 237, 0, 0),
  FORET("Forêt", 34, 139, 34),
  MANGROVE("Mangrove", 84, 249, 141),
  PERMAFROST("Permafrost", 201, 160, 220),
  FORETTROPICALE("Forêt tropicale", 1, 215, 88),
  JUNGLE("Jungle", 20, 148, 20),
  TOUNDRA("Toundra", 139, 119, 195),
  TAIGA("Taiga", 23, 87, 50),
  PLAINE("Plaine", 87, 213, 59),
  SAVANE("Savane", 194, 247, 50),
  DESERTGLACE("Désert Glacé", 254, 231, 240),
  STEPPE("Steppe", 253, 108, 158),
  PAMPA("Pampa", 108, 2, 119),
  DESERT("Désert", 224, 205, 169),
  HAUTPLATEAU("Haut plateau", 212, 115, 212),
  HAUTEFORETENNEIGEE("Haute forêt enneigée", 248, 142, 85),
  PLATEAUENNEIGEE("Plateau enneigé", 133, 109, 77),
  PLATEAUROCHEUXENNEIGE("Plateau rocheux enneigé", 223, 109, 20),
  HAUTEJUNGLE("Haute jungle", 240, 195, 0),
  PLATEAUROCHEUX("Plateau rocheux", 38, 196, 236),
  HAUTEFORET("Haute forêt", 86, 115, 154),
  PLATEAUVOLVANIQUE("Plateau volcanique", 204, 204, 255),
  MONTAGNE("Montagne", 4, 139, 154),
  MONTAGNEFORETENNEIGEE("Montagne forêt enneigée", 253, 233, 224),
  MONTAGNEENNEIGEE("Montagne enneigée", 37, 253, 233),
  MONTAGNEROCHEUSEENNEIGEE("Montagne rocheuse enneigée", 210, 202, 236),
  MONTAGNEJUNGLE("Montagne jungle", 75, 0, 130),
  MONTAGNEROCHEUSE("Montagne rocheuse", 218, 112, 214),
  MONTAGNEFORET("Montagne forêt", 151, 223, 198),
  VOLCAN("Volcan", 254, 27, 0),
  ;
  
  private String name;
  private int r;
  private int g;
  private int b;
  
  public int getR(){
    return this.r;
  }
  
  public int getG(){
    return this.g;
  }
  
  public int getB(){
    return this.b;
  }
  
  public String getName(){
    return this.name;
  }
  
  private biomes(final String name, final int r, final int g, final int b){
    this.name = name;
    this.r = r;
    this.g = g;
    this.b = b;
  }
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
    float niv0 = (float) oNoise.eval(x / 20.0f, y / 20.0f);
    float niv1 = (float) (0.5f * oNoise.eval(x / 10.0f, y / 10.0f) * niv0);
    float niv2 = (float) (0.25f * oNoise.eval(x / 5.0f, y / 5.0f) * (niv0 + niv1));
    float niv = niv0 + niv1 + niv2;
    //float niv = (float) (oNoise.eval(x / 20.0, y / 20.0) + 0.5 * oNoise.eval(x / 10.0, y / 10.0) + 0.25 * oNoise.eval(x / 5.0, y / 5.0));
    
    float hum0 = (float) oNoise.eval(x / 60.0f, y / 60.0f);
    float hum1 = (float) (0.5f * oNoise.eval(x / 30.0f, y / 30.0f) * hum0);
    float hum2 = (float) (0.25f * oNoise.eval(x / 15.0f, y / 15.0f) * (hum0 + hum1));
    float hum = hum0 + hum1 + hum2;
    
    float tem0 = (float) oNoise.eval(x / 100.0f , y / 100.0f );
    float tem1 = (float) (0.5f * oNoise.eval(2 * x / 100.0f , 2 * y / 100.0f ) * hum0);
    float tem2 = (float) (0.25f * oNoise.eval(4 * x / 100.0f , 4 * y / 100.0f ) * (hum0 + hum1));
    float tem = tem0 + tem1 + tem2;
    Cell c = new Cell(niv, hum, tem);
    world.put(k, c);
  }
  
  
  
// ==============
// Partie visuelle
// ==============
  public Set<Biome> draw(int x, int y, int nb, float size){
    Set<Biome> legende = new HashSet<Biome>();
    float cx = x * size * 3 / 2;
    float cy = y * sqrt(3) * size + x % 2 * sqrt(3) * size / 2;
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
          legende.add(c.getBiome());
          translate(height / 2 - cx, height / 2 - cy);
          c.draw((x + px) * 3 * size / 2, (y + py) * sqrt(3) * size + abs(x + px) % 2 * sqrt(3) * size / 2, size);
          fill(0);
          translate(-height / 2 + cx, - height / 2 + cy);
        }
      }
    }
    return legende;
  }
}
  public void settings() {  size(900, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SimuLifeV2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
