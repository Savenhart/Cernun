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
