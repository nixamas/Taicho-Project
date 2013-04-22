package com.cosmichorizons.enums;


import java.util.ArrayList;

import com.cosmichorizons.interfaces.MoveManager;

/**
 * Legal move values for lvl2 ranked characters
 * @author Ryan
 *
 */
public enum LevelTwoLegalMoves implements MoveManager{
	MOVE_ONE(2), MOVE_TWO(18), MOVE_THREE(1), MOVE_FOUR(9),
		MOVE_FIVE(-2), MOVE_SIX(-18), MOVE_SEVEN(-1), MOVE_EIGHT(-9);

    private int numVal;

    LevelTwoLegalMoves(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
    
    @Override
	public Object[] getMoves() {
		return values();
	}
    
    @Override
	public int getMove(int i) {
    	LevelTwoLegalMoves[] array = values();
		return array[i].getNumVal();
	}
    
	public static int getBufferValue() {
		return 2;
	}
	
	/**
	 * +9,+18 --> M4,M2
	 * -1,-2  --> M7,M5
	 * -9,-18 --> M8,M6
	 * +1,+2  --> M3,M1
	 */
	public static ArrayList<ArrayList<MoveManager>> getBlockablePathsOfMoves(){
		ArrayList<ArrayList<MoveManager>> moves = new ArrayList<ArrayList<MoveManager>>();
		for(int i = 0; i < 4; i++){
			moves.add(new ArrayList<MoveManager>());
			switch(i){
				case 0:
					moves.get(i).add(MOVE_FOUR);
					moves.get(i).add(MOVE_TWO);
					break;
				case 1:
					moves.get(i).add(MOVE_SEVEN);
					moves.get(i).add(MOVE_FIVE);
					break;
				case 2:
					moves.get(i).add(MOVE_EIGHT);
					moves.get(i).add(MOVE_SIX);
					break;
				case 3:
					moves.get(i).add(MOVE_THREE);
					moves.get(i).add(MOVE_ONE);
					break;
				default:
					break;
			}
		}
		return moves;
	}
}
