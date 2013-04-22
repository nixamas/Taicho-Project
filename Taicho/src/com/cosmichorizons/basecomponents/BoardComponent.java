package com.cosmichorizons.basecomponents;
import java.awt.Color;

import com.cosmichorizons.characters.EmptyObject;
import com.cosmichorizons.enums.Location;
import com.cosmichorizons.enums.Player;
import com.cosmichorizons.enums.TaichoColors;


/**
 * Each square on the board is made of its own BoardComponent. 
 * BoardComponent will hold the character if it is occupied. 
 * Each BC will have a coordinate that corresponds to a position of the board and 
 * 		an index value which can be seen in the board.xls file.
 * @author Ryan
 *
 */
public class BoardComponent {

	private final Coordinate coordinate;
	private final Location location;
	private Color color = Color.BLACK;
	private boolean occupied = false;
	private boolean stackable = false;
	private boolean selected = false;
	private boolean attackable = false;
	private boolean barrier, timeServed;
	private MovableObject character;
	private boolean highlight;

	public BoardComponent(MovableObject character, Location loc, Coordinate coord) {
		this.occupied = true;
		this.character = character;
		this.location = loc;
		this.coordinate = coord;
	}
	public BoardComponent(Location loc, Coordinate coord){
		this.occupied = false;
		this.location = loc;
		this.coordinate = coord;
		this.character = new EmptyObject();
	}
	public boolean isOccupied() {
		if(this.character.getPlayer() != Player.NONE){
			return true;
		}else{
			return false;
		}
//		return occupied;
	}
	public MovableObject getCharacter() {
		if(character != null){
			return character;
		}else{
			return new EmptyObject();
		}
	}
	public void setCharacter(MovableObject character) {
		this.character = character;
	}
	public MovableObject removeCharacter(){
		MovableObject tempChar= this.character;
		this.character = new EmptyObject();
		return tempChar;
	}
	public Location getLocation() {
		return location;
	}
	public int getId() {
		return coordinate.getId();
	}
	public void setId(int id) {
		this.coordinate.setId(id);
	}
	public Coordinate getCoordinate() {
		return coordinate;
	}
//	public void setCoordinate(Coordinate coordinate) {
//		this.coordinate = coordinate;
//	}
	public Color getColor() {
		if(selected){
			return TaichoColors.GAME_BOARD_SELECTED.getColor();
		}else if(highlight){
			return TaichoColors.GAME_BOARD_HIGHLIGHT.getColor();
		}else if(stackable){
			return TaichoColors.GAME_BOARD_STACKABLE.getColor();
		}else if(attackable){
			return TaichoColors.GAME_BOARD_ATTACKABLE.getColor();
		}else{
			return color;
		}
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isHighlight() {
		return highlight;
	}
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	public boolean isStackable() {
		return stackable;
	}
	public void setStackable(boolean stackable) {
		this.stackable = stackable;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isAttackable() {
		return attackable;
	}
	public void setAttackable(boolean attackable) {
		this.attackable = attackable;
	}
	public boolean isBarrier() {
		return barrier;
	}
	public void setBarrier(boolean barrier) {
		this.barrier = barrier;
	}
	public boolean isTimeServed() {
		return timeServed;
	}
	public void setTimeServed(boolean timeServed) {
		this.timeServed = timeServed;
	}
	
	public String toDebugString(String spltr) {
//		return "BoardComponent [coordinate=" + coordinate + ", location="
//				+ location + ", color=" + color + ", occupied=" + occupied
//				+ "\n\t" + "highlight=" + highlight + ", character=" + character + "]";
		StringBuffer buff = new StringBuffer();
		buff.append("BoardComponent :: " + coordinate + spltr);
		buff.append("Location :: " + location + spltr);
		buff.append("high-occ :: " + highlight + "-" +occupied + spltr);
		buff.append("Char :: " + character + spltr);
		
		return buff.toString();
	}
	
	@Override
	public String toString() {
		return "BoardComponent [coordinate=" + coordinate + ", location="
				+ location + ", color=" + color + ", occupied=" + occupied
				+ "\n\t" + "highlight=" + highlight + ", character=" + character + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((coordinate == null) ? 0 : coordinate.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
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
		BoardComponent other = (BoardComponent) obj;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		if (location != other.location)
			return false;
		return true;
	}
}
