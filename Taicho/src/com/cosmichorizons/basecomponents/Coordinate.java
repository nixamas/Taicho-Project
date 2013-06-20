package com.cosmichorizons.basecomponents;

/**
 * Every Board Component on the board will contain a coordinate object. 
 * Look in the board.xls file for the board layout.
 * 
 * @author Ryan
 *
 */
public class Coordinate {
	private int id;
	private int posX, posY;
	public Coordinate(int posX, int posY, int id) {
		super();
		this.id = id;
		this.posX = posX;
		this.posY = posY;
	}
	public Coordinate(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
		this.id = -1;
	}
	public Coordinate(){
		this.posX = -1;
		this.posY = -1;
		this.id = -1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosX(int x) {
		this.posX = x;
	}
	public void setPosY(int y) {
		this.posY = y;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + posX;
		result = prime * result + posY;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (posX != other.posX)
			return false;
		if (posY != other.posY)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Coordinate [posX=" + posX + ", posY=" + posY + "]";
	}
	public String toSaveString() {
		return "{Coordinate:[id=" + id + ", posX=" + posX + ", posY=" + posY + "]}";
	}
	
}
