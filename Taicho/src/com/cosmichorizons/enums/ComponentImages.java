package com.cosmichorizons.enums;

/**
 * Not important to game functionality but used to display icons instead of shapes
 * Each enum corresponds to a icon that will be used. The location field stores the full directory path of the image
 * and the name member obviously stores the name. 
 * @author Ryan
 *
 */
public enum ComponentImages {
	LEVEL_ONE_IMAGE("images/", "levelOneImage.jpg"), 
	LEVEL_TWO_IMAGE("images/", "levelTwoImage.jpg"), 
	LEVEL_THREE_IMAGE("images/", "levelThreeImage.jpg"), 
	TAICHO_IMAGE("images/", "TaichoImage.png"), 
	GAME_BOARD_IMAGE("images/", ""), 
	OUT_OF_BOUNDS_IMAGE("images/", ""),
	NONE("", "");
	
	private String location;
	private String name;

	ComponentImages(String loc, String n) {
        this.location = loc + n;
        this.name = n;
    }

    public String getImageLocation() {
        return this.location;
    }
    
    public String getImageName() {
    	return this.name;
    }
    
    public String getThumbnailLocation(){
    	return "thumbnails/" + this.name;
    }

}
