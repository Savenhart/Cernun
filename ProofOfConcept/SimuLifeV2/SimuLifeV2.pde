World monde;

Cell current;

//zoom
int min = 5;
int max = 50;
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
  size(600, 600);
  //fullScreen();
  
  //initialisation zoom
  nbCell = 10;
  pas = (max - min) / nbZoom;
  
  //Initialisation monde
  //monde = new World("Monde 1", 46854);
  monde = new World("Monde 1", round(random(999999999)));
  
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
  scl = (height / 2)/(2.0 * nbCell + 1);
  monde.draw(origX, origY, nbCell, scl);
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
