import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

class World{
  
  Map<Key, Cell> world;
  Map<Key, Food> foods;
  String name;
  long seed;
  int id;
  
  OpenSimplexNoise oNoise;
  
  public World(String n, long s){
    name = n;
    seed = s;
    oNoise = new OpenSimplexNoise(seed);
    world = new HashMap<Key, Cell>();
    foods = new HashMap<Key, Food>();
  }
  
  public void genCell(int x, int y){
    Key k = new Key(x, y);
    float niv0 = (float) oNoise.eval(x / 20.0, y / 20.0);
    float niv1 = (float) (0.5 * oNoise.eval(x / 10.0, y / 10.0) * niv0);
    float niv2 = (float) (0.25 * oNoise.eval(x / 5.0, y / 5.0) * (niv0 + niv1));
    float niv = niv0 + niv1 + niv2;
    
    float hum0 = (float) oNoise.eval(x / 60.0, y / 60.0);
    float hum1 = (float) (0.5 * oNoise.eval(x / 30.0, y / 30.0) * hum0);
    float hum2 = (float) (0.25 * oNoise.eval(x / 15.0, y / 15.0) * (hum0 + hum1));
    float hum = hum0 + hum1 + hum2;
    
    float tem0 = (float) oNoise.eval(x / 100.0 , y / 100.0 );
    float tem1 = (float) (0.5 * oNoise.eval(2 * x / 100.0 , 2 * y / 100.0 ) * hum0);
    float tem2 = (float) (0.25 * oNoise.eval(4 * x / 100.0 , 4 * y / 100.0 ) * (hum0 + hum1));
    float tem = tem0 + tem1 + tem2;
    Cell c = new Cell(niv, hum, tem);
    world.put(k, c);
    
    genFood(x, y, c.biome.getBiome().getRay());
  }
  
  public void genFood(int x, int y, int rank){
    Key k = new Key(x, y);
    double max = 0.0;
    double val = 0.0;
    for(int nx = x - rank; nx < x + rank; nx++){
      for(int ny = y - rank; ny < y + rank; ny++){
        double temp = oNoise.eval(nx, ny);
        if(temp > max){
          max = temp;
        }
        if(nx ==x && ny == y){
          val = temp;
        }
      }
    }
    if(max == val){
      Food f = new Food();
      foods.put(k, f);
      System.out.println(k.posX + " " + k.posY);
    }
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
          float posX = (x + px) * 3 * size / 2;
          float posY = (y + py) * sqrt(3) * size + abs(x + px) % 2 * sqrt(3) * size / 2;
          c.draw(posX, posY, size);
          if(foods.containsKey(k)){
            Food f = foods.get(k);
            f.draw(posX, posY, size);
          }
          fill(0);
          translate(-height / 2 + cx, - height / 2 + cy);
        }
      }
    }
    return legende;
  }
}
