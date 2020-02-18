import java.util.Objects;

class Key{
  
  int posX, posY;
  
  public Key(int x, int y){
    posX = x;
    posY = y;
  }
  
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Key k = (Key) o;
      return posX == k.posX &&
              posY == k.posY;
  }

  @Override
  public int hashCode() {
      return Objects.hash(posX, posY);
  }
}
