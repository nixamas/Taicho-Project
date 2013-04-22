package com.cosmichorizons.enums;

/**
 * Enum for all possible character ranks in the game
 * @author Ryan
 *
 */
public enum Ranks {
		LEVEL_ONE(1),
		LEVEL_TWO(2),
		LEVEL_THREE(3),
		TAICHO(4),
		NONE(0);
		
	    private int numVal;

	    Ranks(int numVal) {
	        this.numVal = numVal;
	    }

	    public int getNumVal() {
	        return numVal;
	    }
}
