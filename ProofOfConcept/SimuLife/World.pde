import java.util.HashMap;
import java.util.Map;

class World{
  
  Map<Key, Cell> world;
  String name;
  long seed;
  int id;
  
  public World(String n, long s){
    name = n;
    seed = s;
    noiseSeed(seed);
    world = new HashMap<Key, Cell>();
  }
  
  public void genCell(int x, int y){
    Key k = new Key(x, y);
    float niv = noise((x + 0) / 20.0, (y + 0) / 20.0);
    float hum = noise(0.2 * (x + 150) / 20.0, 0.2 * (y + 150) / 20.0);
    Cell c = new Cell(niv, hum);
    world.put(k, c);
  }
  
  public void draw(int x, int y, int nb, int size){
    float cx = x * sqrt(3) * size + y % 2 * sqrt(3) * size / 2;
    float cy = y * size * 3 / 2;
    for(int i = x - nb; i <= x + nb; i++){
      for(int j = y - nb; j <= y + nb; j++){
        Key k = new Key(i, j);
        if(!world.containsKey(k)){
          genCell(i, j);
        }
        Cell c = world.get(k);
        translate(width / 2 - cx, height / 2 - cy);
        c.draw(i * sqrt(3) * size + j % 2 * sqrt(3) * size / 2, j * 3 * size / 2, size);
        translate(-width / 2 + cx, - height / 2 + cy);
      }
    }
  }
  
  public void debug(){
    for(Map.Entry<Key , Cell> entry : world.entrySet()){
      System.out.println(entry.getKey().posX + " " + entry.getKey().posY + " | " + entry.getValue().getBiome());
    }
  }
}
