import java.util.HashMap;
import java.util.Map;

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
    float niv = (float) oNoise.eval((x + 00) / 20.0, (y + 00) / 20.0);
    float hum = (float) oNoise.eval(0.2 * (x + 00) / 20.0, 0.2 * (y + 00) / 20.0);
    Cell c = new Cell(niv, hum);
    world.put(k, c);
  }
  
  
  
// ==============
// Partie visuelle
// ==============
  public void draw(int x, int y, int nb, float size){
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
          translate(width / 2 - cx, height / 2 - cy);
          c.draw((x + px) * 3 * size / 2, (y + py) * sqrt(3) * size + abs(x + px) % 2 * sqrt(3) * size / 2, size);
          fill(0);
          translate(-width / 2 + cx, - height / 2 + cy);
        }
      }
    }
  }
}
