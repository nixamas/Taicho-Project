package com.cosmichorizons.enums;

import java.awt.Color;

/**
 * Enum for custom colors. 
 * 
 * Provides an easy way to change the colors of 
 * the board without hunting down code
 * @author Ryan
 *
 */
public enum TaichoColors {
	PLAYER_ONE_LVL1(new Color(247, 213, 59)),		//'yellow'
	PLAYER_ONE_LVL2(new Color(191, 162, 46)),
	PLAYER_ONE_LVL3(new Color(122, 104, 29)),
	PLAYER_TWO_LVL1(new Color(42, 88, 172)),		//'blue'
	PLAYER_TWO_LVL2(new Color(28, 60, 115)),
	PLAYER_TWO_LVL3(new Color(11, 24, 46)),
	GAME_BOARD_LIGHT(new Color(171, 171, 171)),		//gray
	GAME_BOARD_DARK(new Color(84, 84, 84)),			//dark gray
	GAME_BOARD_HIGHLIGHT(new Color(0, 69, 40)),		//green
	GAME_BOARD_SELECTED(new Color(0, 60, 69)),		//light bluish
	GAME_BOARD_STACKABLE(new Color(64, 0, 70)),		//purple
	GAME_BOARD_ATTACKABLE(new Color(70, 0, 7));		//red
	
	private Color col;
	
	TaichoColors(Color c) {
	    this.col = c;
	}
	
	public Color getColor() {
	    return this.col;
	}


}
