package com.cosmichorizons.enums;

import java.util.ArrayList;

import com.cosmichorizons.interfaces.MoveManager;

/**
 * Legal move values for lvl3 ranked characters
 * @author Ryan
 *
 */
public enum LevelThreeLegalMoves implements MoveManager {
	MOVE_ONE(30), MOVE_TWO(24), MOVE_THREE(16), MOVE_FOUR(20), MOVE_FIVE(10), MOVE_SIX(8), 
		MOVE_SEVEN(-30), MOVE_EIGHT(-24), MOVE_NINE(-16), MOVE_TEN(-20), MOVE_ELEVEN(-10), MOVE_TWELVE(-8);

    private int numVal;

    LevelThreeLegalMoves(int numVal) {
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
    	LevelThreeLegalMoves[] array = values();
		return array[i].getNumVal();
	}
    
	public static int getBufferValue() {
		return 3;
	}
	
	/**
	 * +8,+16,+24 --> M6,M3,M2
	 * -10,-20,-30  --> M11,M10,M7
	 * -8,-16,-24 --> M12,M9,M8
	 * +10,+20,+30  --> M5,M4,M1
	 */
	public static ArrayList<ArrayList<MoveManager>> getBlockablePathsOfMoves(){
		ArrayList<ArrayList<MoveManager>> moves = new ArrayList<ArrayList<MoveManager>>();
		for(int i = 0; i < 4; i++){
			moves.add(new ArrayList<MoveManager>());
			switch(i){
				case 0:
					moves.get(i).add(MOVE_SIX);
					moves.get(i).add(MOVE_THREE);
					moves.get(i).add(MOVE_TWO);
					break;
				case 1:
					moves.get(i).add(MOVE_ELEVEN);
					moves.get(i).add(MOVE_TEN);
					moves.get(i).add(MOVE_SEVEN);
					break;
				case 2:
					moves.get(i).add(MOVE_TWELVE);
					moves.get(i).add(MOVE_NINE);
					moves.get(i).add(MOVE_EIGHT);
					break;
				case 3:
					moves.get(i).add(MOVE_FIVE);
					moves.get(i).add(MOVE_FOUR);
					moves.get(i).add(MOVE_ONE);
					break;
				default:
					break;
			}
		}
		return moves;
	}
}
