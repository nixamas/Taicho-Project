package com.cosmichorizons.taicho;

import com.badlogic.gdx.InputProcessor;

public class State implements InputProcessor {
	protected Taicho _parent = null;
	
	public State(Taicho taicho) {
		_parent = taicho;
	}
	
	// GAME LOOP
	
	public void update(double deltaT) {
	
	}
	
	public void render() {
	
	}
	
	// LIFE - CYCLE
	public void pause() {
		
	}
	
	public void resume() {
		
	}

	// EVENTS
	
	@Override
	public boolean keyDown(int arg0) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	public boolean touchMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	// MEMORY MANAGEMENT
	
	public void load() {
		
	}
	
	public void unload() {
	
	}
	
	public void assignResources() {
		_parent.getAssetManager().finishLoading();
	}

	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getStateName(){
		return "StateName";
	}
}