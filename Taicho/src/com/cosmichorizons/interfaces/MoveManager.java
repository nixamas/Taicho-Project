package com.cosmichorizons.interfaces;


/**
 * This class is implemented by the different legal move enums 
 * 			(LevelOneLegalMoves.java, LevelTwoLegalMoves.java, LevelThreeLegalMoves.java)
 * allows us to create an array of MoveManager classes and then fill it with 
 * any of the legal moves just by figuring out what rank the character
 * @author Ryan
 *
 */
public interface MoveManager {
	
	/**
	 * retuns an array of the values 
	 * @return
	 */
	public Object[] getMoves();
	
	/**
	 * get move at that position in the array
	 * @param i
	 * @return
	 */
	public int getMove(int i);
	
	/**
	 * Returns the numerical value (+/-'X) of the move '
	 * @return
	 */
	public int getNumVal();
}
