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
public class ThreeUnit extends MovableObject {

	List<MovableObject> components;
	
	public ThreeUnit(Player p) {
		super(p, Ranks.LEVEL_THREE, ComponentImages.LEVEL_THREE_IMAGE);
		combatValue = 3;
	}
	
	public ThreeUnit(Player p, MovableObject comp1, MovableObject comp2, MovableObject comp3){
		super(p, Ranks.LEVEL_THREE, ComponentImages.LEVEL_THREE_IMAGE);
		components = new ArrayList<MovableObject>();
		components.add(comp1);
		components.add(comp2);
		components.add(comp3);
		combatValue = 3;
	}
	
	public ThreeUnit(Player p, MovableObject comp1, MovableObject comp2){
		super(p, Ranks.LEVEL_THREE, ComponentImages.LEVEL_THREE_IMAGE);
		components = new ArrayList<MovableObject>();
		ArrayList<MovableObject> tempList;
		if(comp1.getRank() == Ranks.LEVEL_TWO){
			TwoUnit tempUnit = (TwoUnit) comp1;
			tempList = tempUnit.getComponents();
			components.add(tempList.get(0));
			components.add(tempList.get(1));
			components.add(comp2);
		}else if(comp2.getRank() == Ranks.LEVEL_TWO){
			TwoUnit tempUnit = (TwoUnit) comp2;
			tempList = tempUnit.getComponents();
			components.add(tempList.get(0));
			components.add(tempList.get(1));
			components.add(comp1);
		}
		combatValue = 3;
	}
	
	public ArrayList<MovableObject> getComponents(){
		return (ArrayList<MovableObject>) this.components;
	}
	
	@Override
	public void setPlayer(Player p) {
		player = p;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public Color getColor(){
		if( this.player == Player.PLAYER_ONE ){
			return TaichoColors.PLAYER_ONE_LVL3.getColor();
		}else if( this.player == Player.PLAYER_TWO ){
			return TaichoColors.PLAYER_TWO_LVL3.getColor();
		}else{
			return Color.WHITE;
		}
//		return Utils.blendColor(this.getPlayer().getColor(), Color.DARK_GRAY, 0.4);
	}
	
	public MovableObject removeUnitFromStack(){
			//remove last unit in array from list
		return this.components.remove( this.components.size() - 1 );
	}
}
