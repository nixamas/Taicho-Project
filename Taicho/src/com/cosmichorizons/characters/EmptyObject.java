package com.cosmichorizons.characters;

import com.cosmichorizons.basecomponents.MovableObject;
import com.cosmichorizons.enums.ComponentImages;
import com.cosmichorizons.enums.Player;
import com.cosmichorizons.enums.Ranks;


/**
 * fills BC's that are not occupied by other (actual game) characters
 * Empty, non null, values
 * Player = Player.None, Rank = Ranks.None, ComponentImages = ComponentImages.None
 * @author Ryan
 *
 */
public class EmptyObject extends MovableObject {

	public EmptyObject() {
		super(Player.NONE, Ranks.NONE);
	}

}
