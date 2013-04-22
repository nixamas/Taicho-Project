package com.cosmichorizons.basecomponents;

/**
 * class containing a start and finish coordinate
 * 
 * Not is if this is actually used anywhere. 
 * @author Ryan
 *
 */
public class ObjectMove {
	Coordinate start;
	Coordinate finish;
	
	public ObjectMove(Coordinate pos1, Coordinate pos2){
		this.start = pos1;
		this.finish = pos2;
	}
    
}
