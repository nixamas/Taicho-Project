package com.cosmichorizons.characters;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.cosmichorizons.basecomponents.MovableObject;
import com.cosmichorizons.enums.ComponentImages;
import com.cosmichorizons.enums.Player;
import com.cosmichorizons.enums.Ranks;
import com.cosmichorizons.enums.TaichoColors;


/**
 * reference basecomponents/MovableObject.java for more information
 * @author Ryan
 */
public class TwoUnit extends MovableObject {

	List<MovableObject> components;
	
	public TwoUnit(Player p) {
		super(p, Ranks.LEVEL_TWO, ComponentImages.LEVEL_TWO_IMAGE);
	}
	
	public TwoUnit(Player p, MovableObject comp1, MovableObject comp2){
		super(p, Ranks.LEVEL_TWO, ComponentImages.LEVEL_TWO_IMAGE);
		components = new ArrayList<MovableObject>();
		components.add(comp1);
		components.add(comp2);
	}
	
	public ArrayList<MovableObject> getComponents(){
		return (ArrayList<MovableObject>) this.components;
	}
	
	public MovableObject removeUnitFromStack(){
			//remove last unit in array from list
		return this.components.remove( this.components.size() - 1 );
	}
	
	@Override
	public Color getColor(){
		if( this.player == Player.PLAYER_ONE ){
			return TaichoColors.PLAYER_ONE_LVL2.getColor();
		}else if( this.player == Player.PLAYER_TWO ){
			return TaichoColors.PLAYER_TWO_LVL2.getColor();
		}else{
			return Color.WHITE;
		}
//		return Utils.blendColor(this.getPlayer().getColor(), Color.GRAY, 0.4);// (, 70);
	}
}
