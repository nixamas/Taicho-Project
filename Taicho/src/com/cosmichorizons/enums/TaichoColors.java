package com.cosmichorizons.enums;

import com.badlogic.gdx.graphics.Color;

//import java.awt.Color;

/**
 * Enum for custom colors. 
 * 
 * Provides an easy way to change the colors of 
 * the board without hunting down code
 * @author Ryan
 *
 */
public enum TaichoColors {
	PLAYER_ONE_LVL1(new Color((float)247, (float)213, (float)59, (float)1.0)),		//'yellow'
	PLAYER_ONE_LVL2(new Color((float)191, (float)162, (float)46, (float)1.0)),
	PLAYER_ONE_LVL3(new Color((float)122, (float)104, (float)29, (float)1.0)),
	PLAYER_TWO_LVL1(new Color((float)42, (float)88, (float)172, (float)1.0)),		//'blue'
	PLAYER_TWO_LVL2(new Color((float)28, (float)60, (float)115, (float)1.0)),
	PLAYER_TWO_LVL3(new Color((float)11, (float)24, (float)46, (float)1.0)),
	GAME_BOARD_LIGHT(new Color((float)171, (float)171, (float)171, (float)1.0)),		//gray
	GAME_BOARD_DARK(new Color((float)84, (float)84, (float)84, (float)1.0)),			//dark gray
	GAME_BOARD_HIGHLIGHT(new Color((float)0, (float)69, (float)40, (float)1.0)),		//green
	GAME_BOARD_SELECTED(new Color((float)0, (float)60, (float)69, (float)1.0)),		//light bluish
	GAME_BOARD_STACKABLE(new Color((float)64, (float)0, (float)70, (float)1.0)),		//purple
	GAME_BOARD_ATTACKABLE(new Color((float)70, (float)0, (float)7, (float)1.0));		//red
	
	private Color col;
	
	TaichoColors(Color c) {
	    this.col = c;
	}
	
	public Color getColor() {
	    return this.col;
	}


}
