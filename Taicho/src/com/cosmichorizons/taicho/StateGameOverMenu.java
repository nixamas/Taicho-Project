package com.cosmichorizons.taicho;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.cosmichorizons.enums.Player;

public class StateGameOverMenu extends State {

	// Possible menu states
	public enum State { Loading,
						TransitionIn,
						Active,
						TransitionOut };
	
	// Current menu state
	private State _state;
	
	// Textures
	private TextureRegion _imgBackground;
	
	// Font
	private BitmapFont _fontMenu;
	private BitmapFont _fontLoading;
	
	// Options
	private int _selectedOption;
	private Array<Pair<String, String>> _options;
	
	// Positions
	private Vector2 _menuStart;
	private Vector2 _menuEnd;
	private Vector2 _infoBanner;
	private int _menuGap;
	private Vector3 _mousePos;
	
	// Lang
	LanguagesManager _lang;
	
	private boolean _readyToChange;
	
	private Player _gameWinner;
	
	public StateGameOverMenu(Taicho taicho) {
		super(taicho);
		
		// Languages manager
		_lang = LanguagesManager.getInstance();
				
		// Initial state
		_state = State.Loading;
		
		_gameWinner = Player.NONE;	//no winner set yet
		
		// Resources are initially null
		_imgBackground = null;
//		_imgLogo = null;
//		_imgHighlight = null;
		_fontMenu = null;
		
		// Load font resource
		_fontLoading = Taicho.getPlatformResolver().loadFont("data/eordeoghlakat.fnt", "data/eordeoghlakat.ttf", 70);
		
		// Menu options
		_selectedOption = 0;
		_options = new Array<Pair<String, String>>();
		_options.add(new Pair(_lang.getString("New Game"), "StateGame"));
//		_options.add(new Pair(_lang.getString("How to play"), "StateHowto"));
//		_options.add(new Pair(_lang.getString("How to play"), "StateGame"));
		_options.add(new Pair(_lang.getString("Other Games"), "StateGame"));
		
		if (Gdx.app.getType() != ApplicationType.WebGL) {
			_options.add(new Pair(_lang.getString("Exit"), "StateQuit"));
		}
		
		// Mouse pos
		_mousePos = new Vector3();
		
		_readyToChange = false;
	}
	
	@Override
	public void load() {
		AssetManager assetManager = _parent.getAssetManager();
		
		// Load textures
		assetManager.load("data/taicho_banner.png", Texture.class);
		assetManager.load("data/myTaichoBanner.png", Texture.class);
		
		// Load fonts
		_fontMenu = Taicho.getPlatformResolver().loadFont("data/eordeoghlakat.fnt", "data/eordeoghlakat.ttf", 60);
		
	}
	
	@Override
	public void unload() {
		// Set references to null
		_imgBackground = null;
		_fontMenu = null;
		
		// Unload resources
		AssetManager assetManager = _parent.getAssetManager();
		assetManager.unload("data/taicho_banner.png");
		assetManager.unload("data/myTaichoBanner.png");
		
	}
	
	@Override
	public void assignResources() {
		// Retrieve resources
		AssetManager assetManager = _parent.getAssetManager();
		_imgBackground = new TextureRegion(assetManager.get("data/myTaichoBanner.png", Texture.class));
		
		_imgBackground.flip(false, true);
		
		// Set positions now that we now about sizes
		float maxWidth = 0;
		int numOptions = _options.size;
		
		for (int i = 0; i < numOptions; ++i) {
			TextBounds bounds = _fontMenu.getBounds(_options.get(i).getFirst());
			
			if (bounds.width > maxWidth) {
				maxWidth = bounds.width;
			}
		}
		
		_infoBanner = new Vector2((Taicho.VIRTUAL_WIDTH - maxWidth) / 2 - 185, 320);
		
		_menuStart = new Vector2((Taicho.VIRTUAL_WIDTH - maxWidth) / 2, 390);
		_menuGap = 100;
		_menuEnd = new Vector2(_menuStart.x + maxWidth, 350 + _options.size * _menuGap);
		
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void update(double deltaT) {
		if (_state == State.Loading) {
			if (_parent.getAssetManager().update()) {
				assignResources();
				_state = State.TransitionIn;
			}
			
			return;
		}
		else if (_state  == State.TransitionIn) {
	        	_state = State.Active;
	    }
	    else if (_state == State.Active) {
	    	
	    }
	    else if (_state == State.TransitionOut) {

	    }
	}
	
	@Override
	public void render () {
		SpriteBatch batch = _parent.getSpriteBatch();
		
		// STATE LOADING - Just render loading
		if (_state == State.Loading) {
			String loading = _lang.getString("Loading...");
			TextBounds bounds = _fontLoading.getBounds(loading);
			_fontLoading.draw(batch,
						     loading,
						     (Taicho.VIRTUAL_WIDTH - bounds.width) / 2,
						     (Taicho.VIRTUAL_HEIGHT - bounds.height) / 2);
			
			return;
		}
		
	    batch.draw(_imgBackground, 0, 0);
	    
	    if( this._gameWinner != Player.NONE ){
	    	_fontMenu.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			_fontMenu.draw(batch, "CONGRATULATIONS " + this._gameWinner.getUserReadableString(), _infoBanner.x, _infoBanner.y );
	    }
	    
	    
	    int numOptions = _options.size;
		for (int i = 0; i < numOptions; ++i) {
			TextBounds bounds = _fontMenu.getBounds(_options.get(i).getFirst());
			
			_fontMenu.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			_fontMenu.draw(batch, _options.get(i).getFirst(), (Taicho.VIRTUAL_WIDTH - bounds.width) / 2, _menuStart.y + i * _menuGap + 4);
			_fontMenu.setColor(Color.BLACK.cpy());
	        _fontMenu.draw(batch, _options.get(i).getFirst(), (Taicho.VIRTUAL_WIDTH - bounds.width) / 2, _menuStart.y + i * _menuGap);
		}
	}
	
	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// Left click		
		if (arg3 == 0) {
			int currentOption = getOption();
			
			if (currentOption > 1 && Gdx.app.getType() == ApplicationType.WebGL) {
				currentOption = 0;
			}
			
			if (_readyToChange && currentOption == _selectedOption) {
				_parent.changeState(_options.get(_selectedOption).getSecond());
			}
			else {
				_readyToChange = true;
				_selectedOption = currentOption;
				_parent.changeState(_options.get(_selectedOption).getSecond());
			}
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		
		return false;
	}
	
	@Override
	public boolean keyDown(int arg0) {
		if(arg0 == Keys.BACK){
			_parent.changeState("StateQuit");
		}
		
		return false;
	}
	
	@Override
	public void resume() {
		_state = State.Loading;
		_readyToChange = false;
	}
	
	private int getOption() {
		// Mouse position and selected option
	    _mousePos.x = Gdx.input.getX();
	    _mousePos.y = Gdx.input.getY();
	    _parent.getCamera().unproject(_mousePos);

	    if (_mousePos.y >= _menuStart.y - 100 && _mousePos.y < _menuEnd.y + 100) {
	       return (int)(_mousePos.y - _menuStart.y) / _menuGap;
	    }
	    
	    return _selectedOption;
	}
	
	public void setGameWinner(Player winner){
		this._gameWinner = winner;
	}
}
