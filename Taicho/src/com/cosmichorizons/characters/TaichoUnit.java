package com.cosmichorizons.characters;

import java.awt.Color;

import com.cosmichorizons.basecomponents.MovableObject;
import com.cosmichorizons.enums.ComponentImages;
import com.cosmichorizons.enums.Player;
import com.cosmichorizons.enums.Ranks;


/**
 * reference basecomponents/MovableObject.java for more information
 * @author Ryan
 */
public class TaichoUnit extends MovableObject {

	public TaichoUnit(Player p) {
		super(p, Ranks.TAICHO, ComponentImages.TAICHO_IMAGE);
	}
	
//	@Override
//	public Color getColor(){
////		return Utils.blendColor(this.getPlayer().getColor(), Color.WHITE, 0.4);
//	}

}
