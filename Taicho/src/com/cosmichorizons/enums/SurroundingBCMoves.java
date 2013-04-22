package com.cosmichorizons.enums;


import java.util.ArrayList;

import com.cosmichorizons.interfaces.MoveManager;

public enum SurroundingBCMoves implements MoveManager{
	MOVE_ONE(1), MOVE_TWO(10), MOVE_THREE(8), MOVE_FOUR(9), 
		MOVE_FIVE(-1), MOVE_SIX(-10), MOVE_SEVEN(-8), MOVE_EIGHT(-9);

    private int numVal;

    SurroundingBCMoves(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }

//	@Override
//	public ArrayList<MoveManager> getMoves() {
//		ArrayList<MoveManager> moves;
//		Object[] surroundingMoves = values();
//		for(Object move : surroundingMoves){
//			MoveManager mmMove = (MoveManager) move;
//			moves.add(mmMove);
//		}
//		return moves;
//	}

	public static ArrayList<MoveManager> getMoveManagerMoves() {
		ArrayList<MoveManager> moves = new ArrayList<MoveManager>();
		Object[] surroundingMoves = values();
		for(Object move : surroundingMoves){
			MoveManager mmMove = (MoveManager) move;
			moves.add(mmMove);
		}
		return moves;
	}
	
	@Override
	public int getMove(int i) {
		SurroundingBCMoves[] array = values();
		return array[i].getNumVal();
	}

	public static int getBufferValue() {
		return 1;
	}

	@Override
	public Object[] getMoves() {
		return values();
	}
}
