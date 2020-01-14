World monde;

Cell current;
Cell next;

int scl = 7;

//déplacement
boolean depg = false;
boolean depd = false;
boolean deph = false;
boolean depb = false;
int origX = 0;
int origY = 0;

public void setup(){
  size(600, 600);
  //fullScreen();
  
  monde = new World("Monde 1", 46854);
  //monde = new World("Monde 1", round(random(999999999)));
  
}

public void draw(){
  
  background(0);
  
  origX += ((depd? 1 : 0) - (depg ? 1 : 0)) * 2;
  origY += ((depb? 1 : 0) - (deph ? 1 : 0)) * 2;
  
  monde.draw(origX, origY, 25, scl);
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
      break;
    case '-':
      scl = scl > 10 ? scl - 10 : 10;
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
