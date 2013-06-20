package com.cosmichorizons.enums;

import com.badlogic.gdx.graphics.Color;

//import java.awt.Color;

/**
 * Enum for all possible players of the game
 * @author Ryan
 *
 */
public enum Player {
	NONE{
//		Color color = Color.WHITE;
//		public void setColor(Color col){
//			color = col;
//		}
//		public Color getColor(){
//			return color;
//		}
		public String getUserReadableString(){
			return "None";
		}
		public String getName(){
			return "NONE";
		}
	},
	PLAYER_ONE{
//		Color color = TaichoColors.PLAYER_ONE_LVL1.getColor();
//		public void setColor(Color col){
//			color = col;
//		}
//		public Color getColor(){
//			return color;
//		}
		public String getUserReadableString(){
			return "Player One";
		}
		public String getName(){
			return "PLAYER_ONE";
		}
	},
	PLAYER_TWO{
//		Color color = TaichoColors.PLAYER_TWO_LVL1.getColor();
//		public void setColor(Color col){
//			color = col;
//		}
//		public Color getColor(){
//			return color;
//		}
		public String getUserReadableString(){
			return "Player Two";
		}
		public String getName(){
			return "PLAYER_TWO";
		}
	};
	
//	public abstract void setColor(Color col);
//	public abstract Color getColor();
	public abstract String getUserReadableString();
	public abstract String getName();
}
