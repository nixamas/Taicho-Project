package com.cosmichorizons.characters;

import java.awt.Color;

import com.cosmichorizons.basecomponents.MovableObject;
import com.cosmichorizons.enums.ComponentImages;
import com.cosmichorizons.enums.Player;
import com.cosmichorizons.enums.Ranks;
import com.cosmichorizons.enums.TaichoColors;


/**
 * reference basecomponents/MovableObject.java for more information
 * @author Ryan
 */
public class OneUnit extends MovableObject {
	
	public OneUnit(Player p) {
		super(p, Ranks.LEVEL_ONE, ComponentImages.LEVEL_ONE_IMAGE);
	}

	@Override
	public Color getColor(){
		if( this.player == Player.PLAYER_ONE ){
			return TaichoColors.PLAYER_ONE_LVL1.getColor();
		}else if( this.player == Player.PLAYER_TWO ){
			return TaichoColors.PLAYER_TWO_LVL1.getColor();
		}else{
			return Color.WHITE;
		}
	}

}
