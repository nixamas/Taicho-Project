package com.cosmichorizons.enums;

import java.util.ArrayList;

import com.cosmichorizons.interfaces.MoveManager;


/**
 * Legal move values for lvl1 ranked characters
 * @author Ryan
 *
 */
public enum LevelOneLegalMoves implements MoveManager{
	MOVE_ONE(1), MOVE_TWO(10), MOVE_THREE(8), MOVE_FOUR(9), 
		MOVE_FIVE(-1), MOVE_SIX(-10), MOVE_SEVEN(-8), MOVE_EIGHT(-9);

    private int numVal;

    LevelOneLegalMoves(int numVal) {
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
		LevelOneLegalMoves[] array = values();
		return array[i].getNumVal();
	}

	public static int getBufferValue() {
		return 1;
	}
	
	public static ArrayList<ArrayList<MoveManager>> getBlockablePathsOfMoves(){
		ArrayList<ArrayList<MoveManager>> moves = new ArrayList<ArrayList<MoveManager>>();
		for(int i = 0; i < 8; i++){
			//order doesnt matter here because LevelOne can only move
				//one block in each direction
			moves.add(new ArrayList<MoveManager>());
			moves.get(i).add( values()[i] );
		}
		return moves;
	}
}
