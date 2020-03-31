
import java.util.Set;


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
  size(900, 600);
  //fullScreen();
  
  //initialisation zoom
  pas = (max - min) / nbZoom;
  nbCell = min + pas;
  
  //Initialisation monde
  monde = new World("Monde 1", 42);
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
  scl = (height / 2)/(2.0 * nbCell + 1);
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
