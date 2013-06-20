package com.cosmichorizons.enums;

/**
 * Enum for all possible character ranks in the game
 * LEVEL_ONE, LEVEL_TWO, LEVEL_THREE, TAICHO, NONE
 * @author Ryan
 *
 */
public enum Ranks {
		LEVEL_ONE(1,"LEVEL_ONE"),
		LEVEL_TWO(2,"LEVEL_TWO"),
		LEVEL_THREE(3,"LEVEL_THREE"),
		TAICHO(4,"TAICHO"),
		NONE(0,"NONE");
		
	    private int numVal;
	    private String name;

	    Ranks(int numVal, String _name) {
	        this.numVal = numVal;
	        this.name = _name;
	    }

	    public int getNumVal() {
	        return numVal;
	    }
	    
	    public String getName(){
	    	return this.name;
	    }
}
