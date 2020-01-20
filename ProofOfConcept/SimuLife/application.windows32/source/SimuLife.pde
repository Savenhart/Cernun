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
  size(600, 600);
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
