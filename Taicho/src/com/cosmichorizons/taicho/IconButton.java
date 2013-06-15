package com.cosmichorizons.taicho;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IconButton extends Button{
	private TextureRegion _state1Icon, _state2Icon;
	private enum bstate{ state1, state2 };
	private bstate buttonState;
	public IconButton(Taicho game, int x, int y, String text) {
		super(game, x, y, text);
		// TODO Auto-generated constructor stub
		this.buttonState = bstate.state1;
	}
	public void setButtonIcons(TextureRegion state1Icon, TextureRegion state2Icon){
		this._state1Icon = state1Icon;
		this._state2Icon = state2Icon;
		setIcon(this._state1Icon);
		setButtonSize(this._state1Icon);
		this.buttonState = bstate.state1;
	}

	public void toggleState(){
		if( this.buttonState == bstate.state1 ){
			this.buttonState = bstate.state2;
			if( this._state2Icon != null){
				setIcon(this._state2Icon);
				setButtonSize(this._state2Icon);
			}
		}else if( this.buttonState == bstate.state2 ){
			this.buttonState = bstate.state1;
			if( this._state1Icon != null){
				setIcon(this._state1Icon);
				setButtonSize(this._state1Icon);
			}
		}
	}
	
	public void setButtonSize(TextureRegion icon){
		this._width = icon.getRegionWidth();
		this._height = -icon.getRegionHeight();
	}
	
	@Override
	public boolean isClicked(int mX, int mY) {
		if (mX > _pos.x &&
				mX < _pos.x + _width &&
			    mY > _pos.y &&
			    mY < _pos.y + _height)
			{
				_clicked = true;
				return true;
			}
			else {
				return false;
			}
	}
}
