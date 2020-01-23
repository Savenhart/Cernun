import java.util.HashSet;

HashSet<Cell> grid = new HashSet<Cell>();

Cell current;
Cell next;

int nbX, nbY;
int scl = 7;
int cols, rows;
float freq = 0.2;
float inc = 0.05;
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
  fullScreen();
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
      float niv = noise(x / 20.0, y / 20.0);
      float hum = noise(freq * x / 20.0, freq * y / 20.0);
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
