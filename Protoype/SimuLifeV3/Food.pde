class Food{
  
  public Food(){
    
  }

  public void draw(float x, float y, float scl){
    stroke(255);
    fill(0);
    translate(x, y);
    circle(0, 0, scl);
    translate(-x, -y);
  }

}
