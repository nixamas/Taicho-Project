package com.cosmichorizons.utilities;

/**
 * Convienence class that allows us to dynamically create the onscreen board in different dimensions
 * @author Ryan
 *
 */
public class BoardDimensions {
	private final int COMPONENT_DIMENSION;
	private final int CHARACTER_DIMENSION;
	private final int BOARD_LENGTH;
	private final int BOARD_WIDTH;
	private final int CHARACTER_OFFSET;
	private final int BORDER_SIZE;
	
	private int initialDimension = 20;
	
	public BoardDimensions(int i){
		if( i > 20 ){
			this.initialDimension = i;
		}
		this.COMPONENT_DIMENSION = this.initialDimension;
		this.BOARD_LENGTH = (this.initialDimension * 15 + 4);
		this.BOARD_WIDTH = (this.initialDimension * 9 + 4);
		this.CHARACTER_DIMENSION = (int)(this.initialDimension * 0.65);//- 10); //5
		this.CHARACTER_OFFSET = ((this.COMPONENT_DIMENSION - this.CHARACTER_DIMENSION) / 2);
		
		if( ( i/10 ) < 2){
			this.BORDER_SIZE = 2;
		}else{
			this.BORDER_SIZE = i / 10;
		}
	}

	public int getInitialDimension() {
		return this.initialDimension;
	}
	
	public int getComponentSize(){
		return this.COMPONENT_DIMENSION;
	}
	
	public int getBoardLength(){
		return this.BOARD_LENGTH;
	}
	public int getBoardWidth(){
		return this.BOARD_WIDTH;
	}

	public int getCharacterDimension() {
		return this.CHARACTER_DIMENSION;
	}
	
	public int getCharacterOffset() {
		return this.CHARACTER_OFFSET;
	}

	public int getBorderSize() {
		return BORDER_SIZE;
	}
	
}
