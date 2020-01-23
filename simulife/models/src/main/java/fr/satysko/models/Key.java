package fr.satysko.models;

import java.util.Objects;

public class Key {
	
	private int posX, posY;
	
	public Key(int x, int y) {
		posX= x;
		posY= y;
	}
	
	
	public int getPosX() {
		return posX;
	}


	public void setPosX(int posX) {
		this.posX = posX;
	}


	public int getPosY() {
		return posY;
	}


	public void setPosY(int posY) {
		this.posY = posY;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o ==null || getClass() != o.getClass()) return false;
		Key k = (Key) o;
		return posX == k.getPosX() && posY == k.getPosY();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(posX, posY);
	}

}
