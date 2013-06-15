package com.cosmichorizons.basecomponents;

import com.cosmichorizons.characters.EmptyObject;
import com.cosmichorizons.enums.Player;
import com.cosmichorizons.enums.Ranks;

/**
 * Class used to keep a track of the moves made in the game
 * implemented for undo feature
 * @author Ryan
 *
 */
public class ObjectMove {
	public enum MOVE_TYPE {
		ATTACK,
		MOVE,
		STACK,
		UNSTACK
	}
	
	final private Player player;
	final private Coordinate start;
	final private Coordinate finish;
	final private MOVE_TYPE moveType;
	private MovableObject victimOfAttack; 
	
	public ObjectMove(Player p, Coordinate pos1, Coordinate pos2, ObjectMove.MOVE_TYPE type, MovableObject victim){
		this.player = p;
		this.start = pos1;
		this.finish = pos2;
		this.moveType = type;
		if( this.moveType == ObjectMove.MOVE_TYPE.ATTACK){
			this.victimOfAttack = victim;
		}else{
			this.victimOfAttack = null;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String beg = "ObjectMove [player=" + player + ", start=" + start + ", finish=" + finish + ", moveType=" + moveType;
		if( this.victimOfAttack != null){
			beg += ",\n\t\t character=" + victimOfAttack.toString();
		}
		beg += "]";
		
		return beg;
	}

	public MovableObject resurrectDeadCharacter(){
		return getVictimOfAttack();
	}
	/**
	 * @return the victimOfAttack
	 */
	public MovableObject getVictimOfAttack() {
		if ( victimOfAttack != null){
			return victimOfAttack;
		}else{
			return new EmptyObject();
		}
	}

	/**
	 * @param victimOfAttack the victimOfAttack to set
	 */
	public void setVictimOfAttack(MovableObject victimOfAttack) {
		this.victimOfAttack = victimOfAttack;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the start
	 */
	public Coordinate getStart() {
		return start;
	}

	/**
	 * @return the finish
	 */
	public Coordinate getFinish() {
		return finish;
	}

	/**
	 * @return the moveType
	 */
	public MOVE_TYPE getMoveType() {
		return moveType;
	}
	
//	public ObjectMove(Player p, Coordinate pos1, Coordinate pos2, ObjectMove.MOVE_TYPE type){
//		this.player = p;
//		this.start = pos1;
//		this.finish = pos2;
//		this.moveType = type;
//	}
    
}
