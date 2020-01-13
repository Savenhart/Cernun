float inc = 0.05;
int scl = 10;
int cols, rows;
float[][] niveau;
float[][] humidite;

int centX, centY, nbX, nbY;

String currentB = "";

float freq = 0.2;


public void setup(){
  //size(600, 600);
  fullScreen();
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
  float yoff = 0.0;
  for(int y = 0; y < rows; y ++){
    float xoff = 0.0;
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
