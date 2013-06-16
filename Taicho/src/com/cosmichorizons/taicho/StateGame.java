package com.cosmichorizons.taicho;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Logger;
import com.cosmichorizons.basecomponents.BoardComponent;
import com.cosmichorizons.basecomponents.Coordinate;
import com.cosmichorizons.basecomponents.ObjectMove;
import com.cosmichorizons.enums.Animation;
import com.cosmichorizons.enums.Location;
import com.cosmichorizons.enums.Player;
import com.cosmichorizons.enums.Ranks;
import com.cosmichorizons.exceptions.BoardComponentNotFoundException;
import com.cosmichorizons.gameparts.TaichoGameData;

public class StateGame extends State {

	public enum State {
		Loading,
		InitializeCharacters,
		Wait,
		SelectedCharacter,
		MovingCharacter,
		StackingCharacter,
		UnStackingCharacter,
		AttackingOpponentCharacter,
		ShowingScoreTable
	};
	
//	private static final Vector2 gemsInitial = new Vector2(155, 60);
	private static final Vector2 gemsInitial = new Vector2(3, 3);
	private int SCREEN_SPACER = 80;
	
	private Logger _logger = null;
	// Current game state
	private State _state;
	private boolean _unstackObjects;
	// Selected squares
	ArrayList<BoardComponent> validMoves; 

	private BoardComponent _selectedBC, _destinationBC;

	private TaichoGameData _myTaichoBoard;
		
	// Game elements textures
	private TextureRegion _imgBoard, _imgSelector, _imgLvl1, _imgLvl2, _imgLvl3, _imgTaicho;
	
	// GUI Buttons
	private Button _unstackUnitButton, _exitButton, _submitButton, _backButton;
	private IconButton _musicButton, _soundButton;
	
	//Sounds
	private Music _soundBackgroundMusic;
	private Sound _soundUnitSlide;
	
	// Fonts
	private BitmapFont _debugFont, _fontText, _unstackBtnFontText, _fontLoading;
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"�`'<>";
		
	// Animations
	private double _animTime, _animTotalTime;
//	private double _animTotalInitTime;
	
	private boolean turnSubmit = false, moveMade = false, soundIsOn = true, musicIsOn = true;
	
	// Mouse pos
	private Vector3 _mousePos = null;
	
	// Language manager
	private LanguagesManager _lang;
	
	// Scores table
//	private ScoreTable _scoreTable;
	
	// Aux variables
	private Color _imgColor = Color.WHITE.cpy();
	private Coordinate _coord = new Coordinate();
	
	private boolean DEBUG = Boolean.FALSE;	//controls whether debug data is shown
	private boolean TURNS = Boolean.TRUE;	//controls whether the game controls players turns
	
	
	public StateGame(Taicho taicho) {
		super(taicho);
		
		_logger = new Logger("StateGame");
		_logger.info("StateGame constructor");
		// Languages manager
		_lang = LanguagesManager.getInstance();
		
		// Initial state
		_state = State.Loading;
		_unstackObjects = false;
		// Load and sync loading banner
		_fontLoading = Taicho.getPlatformResolver().loadFont("data/eordeoghlakat.fnt", "data/eordeoghlakat.ttf", 70);
		
		// Create buttons
		_exitButton = new Button(_parent, 20, 630, _lang.getString("Exit"));
		_unstackUnitButton = new Button(_parent, 20, 550, _lang.getString("UnStack Unit"));
		_submitButton = new Button(_parent, 975, 550, _lang.getString("Submit") );
		_backButton = new Button(_parent, 975, 630, _lang.getString("Back") );
		_musicButton = new IconButton(_parent, 975, 20, "");
		_soundButton = new IconButton(_parent, 1020, 20, "");
		
		// Create board
		_myTaichoBoard = new TaichoGameData();
		
		validMoves = new ArrayList<BoardComponent>();
		
		// Mouse pos
		_mousePos = new Vector3();
		
		// Init game for the first time
		init();
	}
	
	@Override
	public void load() {
		AssetManager assetManager = _parent.getAssetManager();
		
		// Load fonts
		
//		_fontTime = Freegemas.getPlatformResolver().loadFont("data/timeFont.fnt", "data/lcd.ttf", 100);
//		_fontScore = Freegemas.getPlatformResolver().loadFont("data/scoreFont.fnt", "data/lcd.ttf", 70);
		
//		_fontText = Freegemas.getPlatformResolver().loadFont("data/normalFont.fnt", "data/normal.ttf", 45);
		_fontText = Taicho.getPlatformResolver().loadFont("data/eordeoghlakat.fnt", "data/eordeoghlakat.ttf", 45);
		_unstackBtnFontText = Taicho.getPlatformResolver().loadFont("data/eordeoghlakat.fnt", "data/eordeoghlakat.ttf", 25);
		_unstackBtnFontText.setColor(Color.BLACK.cpy());
		
		_debugFont = Taicho.getPlatformResolver().loadFont("data/normalFont.fnt", "data/normal.ttf", 20); 
		
		// Load textures
//		assetManager.load("data/scoreBackground.png", Texture.class);
		assetManager.load("data/buttonBackground.png", Texture.class);
		assetManager.load("data/buttonBackgroundPressed.png", Texture.class);
		assetManager.load("data/myTaichoBoard.png", Texture.class);
		assetManager.load("data/selector.png", Texture.class);
//		assetManager.load("data/timeBackground.png", Texture.class);
		assetManager.load("data/gemWhite.png", Texture.class);
		assetManager.load("data/gemOrange.png", Texture.class);
//		assetManager.load("data/iconHint.png", Texture.class);
//		assetManager.load("data/iconRestart.png", Texture.class);
		assetManager.load("data/iconExit.png", Texture.class);
//		assetManager.load("data/iconMusic.png", Texture.class);
		assetManager.load("data/iconUnstack1.png", Texture.class);
		assetManager.load("data/iconLvl1.png", Texture.class);
		assetManager.load("data/iconLvl2.png", Texture.class);
		assetManager.load("data/iconLvl3.png", Texture.class);
		assetManager.load("data/Eastminster.ogg", Music.class);
		assetManager.load("data/slide.ogg", Sound.class);
		assetManager.load("data/myMusicIcon.png", Texture.class);
		assetManager.load("data/myMusicIconOff.png", Texture.class);
		resetGame();
	}
	
	@Override
	public void unload() {
		// Set assets references to null
		_imgBoard = null;
		_imgTaicho = null;
		_imgSelector = null;

		_exitButton.setIcon(null);
		_unstackUnitButton.setIcon(null);
		_submitButton.setIcon(null);
		_backButton.setIcon(null);
		_musicButton.setIcon(null);
		_soundButton.setIcon(null);

		_exitButton.setBackground(null);
		_unstackUnitButton.setBackground(null);
		_submitButton.setBackground(null);
		_backButton.setBackground(null);
		_musicButton.setBackground(null);
		_soundButton.setBackground(null);
		
		_exitButton.setFont(null);
		_unstackUnitButton.setFont(null);
		_submitButton.setFont(null);
		_backButton.setFont(null);
		_musicButton.setFont(null);
		_soundButton.setFont(null);
		
		_soundBackgroundMusic = null;
		_soundUnitSlide = null;
		
		// Unload assets
		AssetManager assetManager = _parent.getAssetManager();
//		assetManager.unload("data/scoreBackground.png");
		assetManager.unload("data/buttonBackground.png");
		assetManager.unload("data/buttonBackgroundPressed.png");
		assetManager.unload("data/myTaichoBoard.png");
		assetManager.unload("data/selector.png");
//		assetManager.unload("data/timeBackground.png");
		assetManager.unload("data/gemWhite.png");
		assetManager.unload("data/gemOrange.png");
//		assetManager.unload("data/iconHint.png");
//		assetManager.unload("data/iconRestart.png");
		assetManager.unload("data/iconExit.png");
//		assetManager.unload("data/iconMusic.png");
		assetManager.unload("data/iconUnstack1.png");
		assetManager.unload("data/iconLvl1.png");
		assetManager.unload("data/iconLvl2.png");
		assetManager.unload("data/iconLvl3.png");
		assetManager.unload("data/Eastminster.ogg");
		assetManager.unload("data/slide.ogg");
		assetManager.unload("data/myMusicIcon.png");
		assetManager.unload("data/myMusicIconOff.png");
	}
	
	@Override
	public void assignResources() {
		super.assignResources();
		
		AssetManager assetManager = _parent.getAssetManager();
		
		// Load textures
//		_imgBoard = new TextureRegion(assetManager.get("data/taicho_board.png", Texture.class));
		_imgBoard = new TextureRegion(assetManager.get("data/myTaichoBoard.png", Texture.class));
		_imgSelector = new TextureRegion(assetManager.get("data/selector.png", Texture.class));
		_imgTaicho = new TextureRegion(assetManager.get("data/gemWhite.png", Texture.class));
		_imgLvl1 = new TextureRegion(assetManager.get("data/iconLvl1.png", Texture.class));
		_imgLvl2 = new TextureRegion(assetManager.get("data/iconLvl2.png", Texture.class));
	    _imgLvl3 = new TextureRegion(assetManager.get("data/iconLvl3.png", Texture.class));
		
		_imgBoard.flip(false, true);
		_imgSelector.flip(false, true);
		_imgTaicho.flip(false, true);
		_imgLvl1.flip(false, true);
		_imgLvl2.flip(false, true);
		_imgLvl3.flip(false, true);
		
		_soundBackgroundMusic = assetManager.get("data/Eastminster.ogg", Music.class);
		_soundUnitSlide = assetManager.get("data/slide.ogg", Sound.class);
		
		// Play music if it wasn't playing
		if (!_soundBackgroundMusic.isPlaying()) {
			_soundBackgroundMusic.setLooping(true);
			_soundBackgroundMusic.play();
		}
				
		// Button textures and font
		TextureRegion buttonBackground = new TextureRegion(assetManager.get("data/buttonBackground.png", Texture.class));
		TextureRegion buttonBackgroundClicked = new TextureRegion(assetManager.get("data/buttonBackgroundPressed.png", Texture.class));
//		TextureRegion iconHint = new TextureRegion(assetManager.get("data/iconHint.png", Texture.class));
//		TextureRegion iconRestart = new TextureRegion(assetManager.get("data/iconRestart.png", Texture.class));
		TextureRegion iconUnstack = new TextureRegion(assetManager.get("data/iconUnstack1.png", Texture.class));
		TextureRegion iconExit = new TextureRegion(assetManager.get("data/iconExit.png", Texture.class));
		TextureRegion iconMusic = new TextureRegion(assetManager.get("data/myMusicIcon.png", Texture.class));
		TextureRegion iconMusicOff = new TextureRegion(assetManager.get("data/myMusicIconOff.png", Texture.class));
//		TextureRegion iconMusic = new TextureRegion(assetManager.get("data/iconMusic.png", Texture.class));
		
		buttonBackground.flip(false, true);
//		iconHint.flip(false, true);
//		iconRestart.flip(false, true);
		iconExit.flip(false, true);
		iconUnstack.flip(false, true);
//		iconMusic.flip(false, true);
		
		_exitButton.setIcon(iconExit);
		_unstackUnitButton.setIcon(iconUnstack);
		_submitButton.setIcon(iconExit);
		_backButton.setIcon(iconExit);		
		iconMusic.flip(false, true);
		iconMusicOff.flip(false, true);
		_musicButton.setButtonIcons(iconMusic, iconMusicOff);
		_soundButton.setButtonIcons(iconExit, iconMusicOff);

		_exitButton.setBackground(buttonBackground);
		_unstackUnitButton.setBackground(buttonBackground);
		_submitButton.setBackground(buttonBackground);
		_backButton.setBackground(buttonBackground);

		_exitButton.setBackgroundClicked(buttonBackgroundClicked);
		_unstackUnitButton.setBackgroundClicked(buttonBackgroundClicked);
		_submitButton.setBackgroundClicked(buttonBackgroundClicked);
		_backButton.setBackgroundClicked(buttonBackgroundClicked);

		_exitButton.setFont(_fontText);
		_unstackUnitButton.setFont(_unstackBtnFontText);
		_submitButton.setFont(_fontText);
		_backButton.setFont(_fontText);

		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void update(double deltaT) {
		
		// Update mouse pos
		_mousePos.x = Gdx.input.getX();
		_mousePos.y = Gdx.input.getY();
		_parent.getCamera().unproject(_mousePos);
		
		// LOADING STATE
		if (_state == State.Loading) {
			// If we finish loading, assign resources and change to FirstFlip state
			if (_parent.getAssetManager().update()) {
				assignResources();
				_state = State.InitializeCharacters;
			}
			return;
		}
				
		// INITIAL GAME STATE
		if (_state == State.InitializeCharacters) {
			//to beginning character animations
				_state = State.Wait;
		}
		
		// WAITING STATE
		if (_state == State.Wait) {
		}
		
//		System.err.print("******* STATE :: " + _state + " with _animTime of :: " + _animTime);
		if( _state == State.MovingCharacter 
//				){
				|| _state == State.AttackingOpponentCharacter
				|| _state == State.StackingCharacter
				|| _state == State.UnStackingCharacter){
//			System.out.println("updating the moving characters ");
			// When animation ends
						if ((_animTime += deltaT) >= _animTotalTime) {
							// Switch to next state, gems start to disappear
							System.out.println("Animation time ended, switching states...");
										
							// Reset animation step
							_animTime = 0;
							
							if( _state == State.MovingCharacter ){
								System.err.println("Sending MakeMove to TaichoGameData... ::: " + _destinationBC.toString());
								_myTaichoBoard.makeMove(_destinationBC.getCoordinate());
								System.err.println("Done sending MakeMove to TaichoGameData!!!");
							}
							else if( _state == State.AttackingOpponentCharacter ){
								System.err.println("Sending AttackMove to TaichoGameData... ::: " + _destinationBC.toString());
//								_myTaichoBoard.attackObject(_destinationBC.getCoordinate());
								_myTaichoBoard.attackObject(_destinationBC);
								System.err.println("Done sending Atack Move to TaichoGameData!!!");
								eraseValidMoves();
								if( !_myTaichoBoard.isGameInPlay() ){
									//GAME IS OVER , GAME OVER , GAMEOVER , GAME_OVER
//									this.resetGame();
									_parent.changeState("StateGameOverMenu");
									
								}
							}else if( _state == State.StackingCharacter){
								System.err.println("Sending StackMove to TaichoGameData... ::: " + _destinationBC.toString());
								_myTaichoBoard.stackUnits(_destinationBC.getCoordinate());
								eraseValidMoves();
							}else if( _state == State.UnStackingCharacter){
								System.err.println("Sending UnStackMove to TaichoGameData... ::: " + _destinationBC.toString());
								_myTaichoBoard.unstackUnits(_destinationBC.getCoordinate());
								eraseValidMoves();
							}
							
							_destinationBC = null;
							_selectedBC = null;
							
							_animTime = 0;
							_state = State.Wait;
							
							this.moveHasBeenMade();
						}
		}
		
		// DISAPPEARING BOARD STATE because there were no possible movements
//	    else if(_state == State.DisappearingBoard) {
//	            _state = State.InitializeCharacters;
//
//	            // Generate a brand new board
//	            _myTaichoBoard = new TaichoGameData();
//
//	    }
	}
	

	@Override
	public void render() {
		SpriteBatch batch = _parent.getSpriteBatch();
		
		// STATE LOADING
		if (_state == State.Loading) {
			String loading = _lang.getString("Loading...");
			TextBounds bounds = _fontLoading.getBounds(loading);
			_fontLoading.draw(batch,
							  loading,
							  (Taicho.VIRTUAL_WIDTH - bounds.width) / 2,
							  (Taicho.VIRTUAL_HEIGHT - bounds.height) / 2);
			
			return;
		}
		
		//storage for animating attacking
		float atckrImgX = 0, atckrImgY = 0, vctmImgX = 0, vctmImgY = 0;
		TextureRegion atckrImg =null, vctmImg = null;
		Color atckrTint = Color.WHITE.cpy(), vctmTint = Color.WHITE.cpy();
		boolean rdyToDrawAtck = false;
		
		
		// Background image
		batch.draw(_imgBoard, 0, 0);
		
//		// Draw buttons
//		_hintButton.render();
//		_resetButton.render();
//		_musicButton.render();
		_exitButton.render();
		_musicButton.render();
		_soundButton.render();
		if( hasMoveBeenMade() ){
			_submitButton.render();
			_backButton.render();
		}

		
		
		// Draw the score
//		batch.draw(_imgScoreBackground, 70, 75);
		_fontText.setColor(Color.BLACK.cpy());
		_fontText.draw(batch, "Turn : ", 10, 20);
		_fontText.draw(batch, _myTaichoBoard.getCurrentPlayerString(), 40, 60);
		
//		_fontScore.draw(batch,
//						"" + _points,
//						452 - _fontScore.getBounds("" + _points).width,
//						93);
		
		// Draw the time
//		batch.draw(_imgTimeBackground, 70, 215);
//		_fontText.draw(batch, _lang.getString("Time left"), 78, 180);
				 
//		_fontTime.draw(batch,
//				_txtTime,
//				390 - _fontTime.getBounds(_txtTime).width,
//				237);
		
		if( false/*this.DEBUG*/ ){	// DEBUG
			LinkedList<ObjectMove> gameMoves = _myTaichoBoard.getMoves();
			for(int i = 0; i < gameMoves.size(); i++){
				_debugFont.draw(batch, gameMoves.get(i).toString(), 20, 20 * i);
			}
		}
		
		// Draw board
		TextureRegion img = null;
		Color colorTint = Color.WHITE.cpy();
		
		if (_state != State.ShowingScoreTable) {
			// Go through all of the squares
	        for (int i = 0; i < 15; i++) { // SIZECHANGE
	            for (int j = 0; j < 9; j++) { // SIZECHANGE
	            	if(_myTaichoBoard.pieceAt(j, i).isOccupied()){
	            		if(_myTaichoBoard.pieceAt(j, i).getCharacter().getPlayer() == _myTaichoBoard.getPlayer1()){
	            			switch(_myTaichoBoard.pieceAt(j, i).getCharacter().getRank()){
		            			case LEVEL_ONE:
		            				img = _imgLvl1;
		            				break;
		            			case LEVEL_TWO:
		            				img = _imgLvl2;
		            				break;
		            			case LEVEL_THREE:
		            				img = _imgLvl3;
		            				break;
		            			case TAICHO:
		            				img = _imgTaicho;
		            				break;		            			
		            			default:
		            				break;
	            			}
	            			colorTint = new Color(0.796f, 0.373f, 0.27f, 1);
	            		}else if(_myTaichoBoard.pieceAt(j, i).getCharacter().getPlayer() == _myTaichoBoard.getPlayer2()){
	            			switch(_myTaichoBoard.pieceAt(j, i).getCharacter().getRank()){
		            			case LEVEL_ONE:
		            				img = _imgLvl1;
		            				break;
		            			case LEVEL_TWO:
		            				img = _imgLvl2;
		            				break;
		            			case LEVEL_THREE:
		            				img = _imgLvl3;
		            				break;
		            			case TAICHO:
		            				img = _imgTaicho;
		            				break;		            			
		            			default:
		            				break;
	            			}
	            			colorTint = new Color(0.388f, 0.412f, 0.263f, 1);
	            		}
	            	}
	            	
	                
	             // Now, if img is not NULL (there's something to draw)
	                if (img != null || _state == State.UnStackingCharacter) {									//TODO: EDIT CODE HERE FOR PLACEMENT OF OBJECTS ON SCREEN
	                    // Default positions
	                    float imgX = gemsInitial.x + i * SCREEN_SPACER;
	                    float imgY = gemsInitial.y + j * SCREEN_SPACER;	                   
	                  	                    	                    
	                    if( _destinationBC != null && _selectedBC != null){	
	                    	if(_state == State.UnStackingCharacter){	                    		
	                    		if(img != null &&
	                    				 (i != _selectedBC.getCoordinate().getPosX() && j != _selectedBC.getCoordinate().getPosY()) ){
//	                    			System.out.println("currently drawing at position of the EVERY OTHER object");
	                    			batch.setColor(colorTint);
	    		                    batch.draw(img, imgX, imgY);
	    		                    batch.setColor(_imgColor);
	                    		}else if( i == _selectedBC.getCoordinate().getPosX() && j == _selectedBC.getCoordinate().getPosY() ){
//	                    			System.out.println("currently drawing at position of the selected object");
	                    			batch.setColor(colorTint);
	    		                    batch.draw(img, imgX, imgY);
	    		                    batch.setColor(_imgColor);
	                    			if(i == _selectedBC.getCoordinate().getPosX() &&  j == _selectedBC.getCoordinate().getPosY() ) {
			                            imgX = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.x + i * SCREEN_SPACER,
			                            		                     (_destinationBC.getCoordinate().getPosX() - _selectedBC.getCoordinate().getPosX()) * SCREEN_SPACER,
			                            		                     _animTotalTime);
			                            imgY = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.y + j * SCREEN_SPACER,
			                            							 (_destinationBC.getCoordinate().getPosY() - _selectedBC.getCoordinate().getPosY()) * SCREEN_SPACER,
			                            							 _animTotalTime);
			                        }
			                        else if (i == _selectedBC.getCoordinate().getPosX() && j == _selectedBC.getCoordinate().getPosY() ){
			                            imgX = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.x + i * SCREEN_SPACER,
			                            							 (_selectedBC.getCoordinate().getPosX() - _destinationBC.getCoordinate().getPosX()) * SCREEN_SPACER,
			                            							 _animTotalTime);
			                            imgY = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.y + j * SCREEN_SPACER,
			                            							 (_selectedBC.getCoordinate().getPosY() - _destinationBC.getCoordinate().getPosY()) * SCREEN_SPACER,
			                            							 _animTotalTime);
			                        }
	                    			batch.setColor(colorTint);
	    		                    batch.draw(img, imgX, imgY);
	    		                    batch.setColor(_imgColor);
	                    		}else if( i == _destinationBC.getCoordinate().getPosX() && j == _destinationBC.getCoordinate().getPosY() ){
//	                    			System.out.println("currently drawing at position of the destination object");
	                    		}
	                    			
	                    	}
	                    	                    	
	                    	if( _state == State.AttackingOpponentCharacter || _state == State.StackingCharacter){
	                    		//get position for the attacking object
	                    		if( i == _selectedBC.getCoordinate().getPosX() && j == _selectedBC.getCoordinate().getPosY() ){
//	                    			System.out.println("currently drawing at position of the selected object");
		                    		if(i == _selectedBC.getCoordinate().getPosX() &&  j == _selectedBC.getCoordinate().getPosY() ) {
			                            imgX = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.x + i * SCREEN_SPACER,
			                            		                     (_destinationBC.getCoordinate().getPosX() - _selectedBC.getCoordinate().getPosX()) * SCREEN_SPACER,
			                            		                     _animTotalTime);
			                            imgY = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.y + j * SCREEN_SPACER,
			                            							 (_destinationBC.getCoordinate().getPosY() - _selectedBC.getCoordinate().getPosY()) * SCREEN_SPACER,
			                            							 _animTotalTime);
			                        }
			                        else if (i == _selectedBC.getCoordinate().getPosX() && j == _selectedBC.getCoordinate().getPosY() ){
			                            imgX = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.x + i * SCREEN_SPACER,
			                            							 (_selectedBC.getCoordinate().getPosX() - _destinationBC.getCoordinate().getPosX()) * SCREEN_SPACER,
			                            							 _animTotalTime);
			                            imgY = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.y + j * SCREEN_SPACER,
			                            							 (_selectedBC.getCoordinate().getPosY() - _destinationBC.getCoordinate().getPosY()) * SCREEN_SPACER,
			                            							 _animTotalTime);
			                        }

	                    			if( !rdyToDrawAtck ){
//	                    				System.out.println("we have the attackers image, gross...");
	                    				atckrImgX = imgX;
	                    				atckrImgY = imgY;
	                    				atckrImg = img;
	                    				atckrTint = colorTint;
	                    				if( vctmImgX != 0 && vctmImgY != 0){
//	                    					System.out.println("Now we have both the attackers position and the victims position, let the massacre begin!!");
	                    					rdyToDrawAtck = true;
	                    				}
	                    			}
	                    		//get position for the victim object
	                    		}else if( i == _destinationBC.getCoordinate().getPosX() && j == _destinationBC.getCoordinate().getPosY() ){
	                    			if( !rdyToDrawAtck ){
//	                    				System.out.println("we have the victimcs image, RUN BEFORE YOU DIE!!!!");
	                    				vctmImgX = imgX;
	                    				vctmImgY = imgY;
	                    				vctmImg = img;
	                    				vctmTint = colorTint;
	                    				if( atckrImgX != 0 && atckrImgY != 0){
//	                    					System.out.println("Now we have both the attackers position and the victims position, let the massacre begin!!");
	                    					rdyToDrawAtck = true;
	                    				}
	                    			}
	                    		//draw all other objects
	                    		}else{
	                    			batch.setColor(colorTint);
	    		                    batch.draw(img, imgX, imgY);
	    		                    batch.setColor(_imgColor);
	                    		}
	                    		
	                    		//if we have both attacker pos and victim pos, lets draw them
	                    		if( rdyToDrawAtck ){
//	                    			System.err.println("Ready to animate the state :::: " + _state);
	                    			batch.setColor(vctmTint);
				                    batch.draw(vctmImg, vctmImgX, vctmImgY);
				                    batch.setColor(_imgColor);
				                    
				                    batch.setColor(atckrTint);
				                    batch.draw(atckrImg, atckrImgX, atckrImgY);
				                    batch.setColor(_imgColor);
				                    System.out.println("...well... I drew the attack, it was awful...");
				                    rdyToDrawAtck = false;
	                    		}
	                    	}//end if( _destinationBC != null && _selectedBC != null){	
	                    	
	                    	
	                    	else if( _state == State.MovingCharacter){
//	                    	System.out.println("animating the moving character*****************");
		                    	if( i == _selectedBC.getCoordinate().getPosX() && j == _selectedBC.getCoordinate().getPosY() ){
		                    		if(i == _selectedBC.getCoordinate().getPosX() &&  j == _selectedBC.getCoordinate().getPosY() ) {
			                            imgX = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.x + i * SCREEN_SPACER,
			                            		                     (_destinationBC.getCoordinate().getPosX() - _selectedBC.getCoordinate().getPosX()) * SCREEN_SPACER,
			                            		                     _animTotalTime);
			                            imgY = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.y + j * SCREEN_SPACER,
			                            							 (_destinationBC.getCoordinate().getPosY() - _selectedBC.getCoordinate().getPosY()) * SCREEN_SPACER,
			                            							 _animTotalTime);
			                        }
			                        else if (i == _selectedBC.getCoordinate().getPosX() && j == _selectedBC.getCoordinate().getPosY() ){
			                            imgX = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.x + i * SCREEN_SPACER,
			                            							 (_selectedBC.getCoordinate().getPosX() - _destinationBC.getCoordinate().getPosX()) * SCREEN_SPACER,
			                            							 _animTotalTime);
			                            imgY = Animation.easeOutQuad(_animTime,
			                            							 gemsInitial.y + j * SCREEN_SPACER,
			                            							 (_selectedBC.getCoordinate().getPosY() - _destinationBC.getCoordinate().getPosY()) * SCREEN_SPACER,
			                            							 _animTotalTime);
			                        }
		                    	}
		                    	batch.setColor(colorTint);
			                    batch.draw(img, imgX, imgY);
			                    batch.setColor(_imgColor);
	                    	}//end if( _state == State.MovingCharacter){
	                    }// end if( _destinationBC != null && _selectedBC != null){	
	                    
	                    if( _state != State.AttackingOpponentCharacter && _state != State.MovingCharacter && _state != State.StackingCharacter && _state != State.UnStackingCharacter){
//	                    	if(_selectedBC != null && _destinationBC != null)
                    		batch.setColor(colorTint);
		                    batch.draw(img, imgX, imgY);
		                    batch.setColor(_imgColor);
                    	}
	                } // End if (img != NULL)
	                img = null;
	                colorTint = Color.WHITE.cpy();
	            }
	            
	            // If the mouse is over a gem
	            if (hoveringOverUnit((int)_mousePos.x, (int)_mousePos.y)) {
	                // Draw the selector over that gem
	            	Coordinate coord = getCoord((int)_mousePos.x, (int)_mousePos.y);
	                batch.draw(_imgSelector,
	                		  (int)gemsInitial.x + coord.getPosX() * SCREEN_SPACER,
	                		  (int)gemsInitial.y + coord.getPosY() * SCREEN_SPACER);
	                
	                
	                //DEBUG DISPLAY**********************************************************************************DEBUG DISPLAY
	                if( this.DEBUG ){
		                BoardComponent hoverBC = _myTaichoBoard.componentFromCoord(coord);
		                _debugFont.draw(batch, "DEBUG : ", 675, 20);
	//	                _debugFont.draw(batch, hoverBC.toDebugString(), 10, 20);
		                String[] debugText = hoverBC.toDebugString("##__##").split("##__##");
		                int ds = 0;
		                for(String s : debugText ){
		                	_debugFont.draw(batch, s, 675, 40 + (ds*50) );
		                	ds++;
		                }
	                }
	                //DEBUG DISPLAY**********************************************************************************DEBUG DISPLAY
	            }
	            
	            // If a gem was previously clicked
	            if(_state == State.SelectedCharacter && _selectedBC != null){
	            	
	                // Draw the tinted selector over it
	            	batch.setColor(1.0f, 0.0f, 1.0f, 1.0f);
	                batch.draw(_imgSelector,
	                		   (int)gemsInitial.x + _selectedBC.getCoordinate().getPosX()* SCREEN_SPACER,
	                		   (int)gemsInitial.y + _selectedBC.getCoordinate().getPosY()* SCREEN_SPACER);
	                batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	                
	                if(_selectedBC.getCharacter().getRank() == Ranks.LEVEL_TWO || _selectedBC.getCharacter().getRank() == Ranks.LEVEL_THREE){
	                	_unstackUnitButton.render();
	                }
	                
	                //highlight valid moves
	                for( int k = 0 ; k < validMoves.size(); k++){
	                	if(validMoves.get(k).isHighlight()){
	                		batch.setColor(0.3f, 0.5f, 0.5f, 1.0f);
	                	}
	                	else if(validMoves.get(k).isStackable()){
	                		batch.setColor(0.0f, 0.5f, 0.5f, 1.0f);
	                	}
	                	else if(validMoves.get(k).isAttackable()){
	                		batch.setColor(0.5f, 0.2f, 0.3f, 1.0f);
	                	}
	                	else if(validMoves.get(k).isOccupied()){
	                		
	                	}
	                	
	                	batch.draw(_imgSelector,
		                		   (int)gemsInitial.x + validMoves.get(k).getCoordinate().getPosX()* SCREEN_SPACER,
		                		   (int)gemsInitial.y + validMoves.get(k).getCoordinate().getPosY()* SCREEN_SPACER);
	                }
	                
	                batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	            }
	        }
		}
	}
	
	@Override
	public boolean keyDown(int arg0) {
		if(arg0 == Keys.BACK){
			_parent.changeState("StateMenu");
		}
		
		return false;
	}
	
	@Override
	public boolean keyUp(int arg0) {
		return false;
	}
	
	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		if (arg3 == 0){ // Left mouse button clicked
			
			if( _state == State.MovingCharacter || _state == State.AttackingOpponentCharacter ){
				return false;	//ignore click
			}
			
	        _mousePos.x = arg0;
	        _mousePos.y = arg1;
	        _parent.getCamera().unproject(_mousePos);
	        Coordinate poc = getCoord((int)_mousePos.x, (int)_mousePos.y);
	        try{
	        	BoardComponent bc = _myTaichoBoard.componentFromCoord(getCoord((int)_mousePos.x, (int)_mousePos.y));
	        	System.err.println("************* BOARD SQUARE DATE *************");
		    	System.out.println("" + bc.toString());
		    	System.err.println("************* BOARD SQUARE DATE *************");
		    	
		    	if(bc.getLocation() != Location.OUT_OF_BOUNDS && !hasMoveBeenMade() ){	//if player clicks on the game board
					if(validMoves.isEmpty() 
							&& _selectedBC == null 
								&& bc.isOccupied() 
									&& ((bc.getCharacterPlayer() == _myTaichoBoard.getCurrentPlayer()) || !this.TURNS) ){		//if there is no valid moves (no BC selected)
//						System.err.println("valid moves is empty, first click");
						selectBoardComponent(poc.getPosY(), poc.getPosX());
//						currentPlayer = bc.getCharacter().getPlayer();
						_selectedBC = bc;
						_destinationBC = null;
						_state = State.SelectedCharacter;
					}else if( !validMoves.isEmpty() && validSelection(bc) && _selectedBC != null){
							// if there is a selected BC and user chose a valid BC
//						System.out.println("make move to new VALID square");
			    		BoardComponent selectedBc = _myTaichoBoard.getSelectedBoardComponent();
			    		
			    		if( bc.isOccupied() && bc.getCharacterPlayer() == selectedBc.getCharacter().getPlayer() ){
			    			//both square are occupied by the same player
//			    			System.err.println("StackUnits");
			    			_destinationBC = _myTaichoBoard.getBoardComponentAtId(bc.getId());
			    			_state = State.StackingCharacter;
			    			_animTime = 0;
			    		}else if( bc.isOccupied() && bc.getCharacterPlayer() != selectedBc.getCharacter().getPlayer() ){
			    			//both squares are occupied by opposite players
//			    			System.err.println("AttackUnits");
			    			_state = State.AttackingOpponentCharacter;
			    			_destinationBC = bc;
			    			_animTime = 0;
			    		}else if( !bc.isOccupied() ){
			    			if(_unstackObjects){
//			    				System.err.println("UnstackUnits");
			    				System.out.println("UNSTACKING CHARACTER UNIT " + _selectedBC.toString() + " @ " + _selectedBC.getCoordinate().toString());
			    				_unstackObjects = false;
			    				_state = State.UnStackingCharacter;
			    				_destinationBC = _myTaichoBoard.componentFromCoord(poc);
			    				_animTime = 0;
			    			}else{
			    				System.err.println("MoveUnits");
			    				_destinationBC = bc;
			    				_state = State.MovingCharacter;
			    				_animTime = 0;
			    				eraseValidMoves();
			    			}
			    		}
			    		if( playSound() ){
				    		//start sliding sound
							_soundUnitSlide.play();
			    		}
					}else{
						//user clicked same BC twice, abort the BC selection
//						System.out.println("checking if user clicked selected BC again. If so, Abort");
			    		BoardComponent selectedBc = _myTaichoBoard.getSelectedBoardComponent();
			    		if(bc.getCoordinate().equals( selectedBc.getCoordinate() )){
			    				//if there is a selected BC then and the user clicked it a second time, 
			    					//Then clear the valid moves array
			    			selectedBc.setSelected(false);
			    			eraseValidMoves();
			    			_state = State.Wait;
			    			_selectedBC = null;
			    		}
					}
		    	}
	        }
	        catch(BoardComponentNotFoundException bcnfe){
	        	System.err.println("TOUCHDOWN_ERR::");
	    		System.err.println(bcnfe.getMessage());
	    	}
	        catch(Exception e){
	        	System.err.println("error occured in touchDown method of StateGame  ::  " + e);
	        }
	        
	        
	        // Buttons 
	        if (_exitButton.isClicked((int)_mousePos.x, (int)_mousePos.y)) {
	            _parent.changeState("StateMenu");
	        }
	        else if( _musicButton.isClicked((int) _mousePos.x, (int) _mousePos.y) ){
	        	System.out.print("music button has been pressed....");
	        	_musicButton.toggleState();
	        	if( _soundBackgroundMusic.isPlaying() ){
	        		_soundBackgroundMusic.pause();
	        	}else if(!_soundBackgroundMusic.isPlaying()){
	        		_soundBackgroundMusic.play();
	        	}
	        }
	        else if(_soundButton.isClicked((int)_mousePos.x, (int)_mousePos.y)){
	        	System.out.println("Sound Button has been pressed, toggling sound");
	        	_soundButton.toggleState();
	        	if( this.soundIsOn == false ){
	        		System.out.println("Turning sound on");
	        		this.soundIsOn = true;
	        	}else if( this.soundIsOn == true ){
	        		System.out.println("Turning sound off");
	        		this.soundIsOn = false;
	        	}
	        }
	        else if(_unstackUnitButton.isClicked((int)_mousePos.x, (int)_mousePos.y)){
	        	if(_unstackObjects){
	        		System.out.println("you chose to unselect the unstack button");
	        		_unstackUnitButton.touchUp();
	        		if(_state == State.SelectedCharacter){
	        			eraseValidMoves();
	        			selectBoardComponent(_selectedBC.getCoordinate().getPosY(), _selectedBC.getCoordinate().getPosX());
	        			_unstackObjects = false;
	        		}
	        	}else
	        	if(_state == State.SelectedCharacter){
	        		System.out.println("you clicked the unstack button");
	        		showValidUnstack();
	        		_unstackObjects = true;
	        	}
	        }
	        else if(_submitButton.isClicked((int)_mousePos.x, (int)_mousePos.y) && hasMoveBeenMade() ){
	        	System.out.println("_submitButton has been pressed");
	        	this.nextTurn();
	        	_submitButton.touchUp();
	        }
	        else if(_backButton.isClicked((int)_mousePos.x, (int)_mousePos.y) && hasMoveBeenMade() ){
	        	System.out.println("_backButton has been pressed");
	        	_myTaichoBoard.undoTurn();
	        	setBeginingOfTurn();
	        	_backButton.touchUp();
	        }
		}

		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		if (arg3 == 0){ // Left mouse button clicked
	        _mousePos.x = arg0;
	        _mousePos.y = arg1;
	        _parent.getCamera().unproject(_mousePos);
	        
	        _exitButton.touchUp();
	        if( !_unstackObjects ){
	        	_unstackUnitButton.touchUp();
	        }
		}
		return false;
	}

    /**
     * if the param bc coordinate member is equal to a coordinate in the valid moves array return true, else false
     * @param bc
     * @return
     */
    private boolean validSelection(BoardComponent bc){
    	for(BoardComponent vbc : validMoves){
    		if(vbc.getCoordinate().equals(bc.getCoordinate())){
    			return true;
    		}
    	}
		return false;
    }
    
	/**
	 * Erases the validMoves array
	 */
	private void eraseValidMoves(){
		for(int i = 0; i < validMoves.size(); i++){
    		validMoves.get(i).setHighlight(false);
    		validMoves.get(i).setStackable(false);
    		validMoves.get(i).setSelected(false);
    		validMoves.get(i).setAttackable(false);
    	}
    	validMoves.clear();
	}
    	
	/**
     * This is called by mousePressed() when a player clicks on the
     * square in the specified row and col. If this BC is a valid selection 
     * get the valid moves that the selected character could travel
     */
    private void selectBoardComponent(int row, int col) {
    	System.out.println("doClickSquare");
    	BoardComponent bc = _myTaichoBoard.pieceAt(row, col);
    	if(bc.isOccupied()){
    		_myTaichoBoard.setCurrentPlayer(bc.getCharacter().getPlayer());
	    	if(validMoves.isEmpty()){
	    		bc.setSelected(true);
	    		System.out.println("you clicked BoardComponent with ID of -- " + bc.getId());
	    		validMoves = bc.getCharacter().getPossibleMoves(_myTaichoBoard, bc);
	    	}
    	}
       	
//    	_myTaichoBoard.getCoordinateOfId(bc.getId());
//    	_myTaichoBoard.getBoardComponentAtId(bc.getId());
    }  
    
	private void init() {
		// Initial animation state
	    _animTime = 0;

	    // Steps for short animations
	    _animTotalTime = 0.5;

	    // Steps for long animations
//	    _animTotalInitTime = 2.0;

	    // Reset the game to the initial values
	    resetGame();
	}
		
	/**
	 * Return true if mouse is over any character on board
	 * @param mX
	 * @param mY
	 * @return
	 */
	private boolean hoveringOverUnit(int mX, int mY) {
		boolean overUnit = false;
		if (mX > gemsInitial.x && mX < gemsInitial.x + SCREEN_SPACER * 15 &&
	            mY > gemsInitial.y && mY < gemsInitial.y + SCREEN_SPACER * 9){
			BoardComponent bc = _myTaichoBoard.componentFromCoord(getCoord(mX, mY));
			if (bc.isOccupied()){
				overUnit = true;
			}
		}
		return overUnit;
	}
	
	private Coordinate getCoord(int mX, int mY) {
		_coord.setPosX((mX - (int)gemsInitial.x) / SCREEN_SPACER);
		_coord.setPosY((mY - (int)gemsInitial.y) / SCREEN_SPACER);
		return _coord;
	}
	
	/**
     * shows the valid moves for a unstacking of a character.
     * @return
     */
    private boolean showValidUnstack(){
    	System.out.println("showValidUnstack method");
    	eraseValidMoves();
    	_unstackObjects = true;
    	BoardComponent selectedBc = _myTaichoBoard.getSelectedBoardComponent();
    	validMoves = selectedBc.getCharacter().getPossibleUnstackLocations(_myTaichoBoard, selectedBc);
    	return true;
    }
	
	private void resetGame() {
		// Reset score
//		_points = 0;
		
		// Generate board
//		_board.generate();
		_myTaichoBoard = new TaichoGameData();
		
		// Set Starting Player
			//may add ability to change later on....
		_myTaichoBoard.nextTurn(Player.PLAYER_ONE);		//set starting turn to player one
		
		// Redraw the scoreboard
//		redrawScoreBoard();
		
		// Restart the time (two minutes)
//		_remainingTime = 120; 
	}
	
	private void nextTurn(){
		if( this._myTaichoBoard.getCurrentPlayer() == Player.PLAYER_ONE ){
			this._myTaichoBoard.nextTurn( Player.PLAYER_TWO );
		}else if( this._myTaichoBoard.getCurrentPlayer() == Player.PLAYER_TWO ){
			this._myTaichoBoard.nextTurn( Player.PLAYER_ONE );
		}else{
			System.err.println("No current player...");
		}
		setBeginingOfTurn();
	}
	
	@Override
	public void resume() {
		_state = State.Loading;
	}
	
	public Player getGameWinner(){
		Player winner = Player.NONE;
		if( !_myTaichoBoard.isGameInPlay() ){
			return _myTaichoBoard.getCurrentPlayer();
		}else{
			System.err.println("Game is still in play");
		}
		return winner;
	}
	
	private boolean hasMoveBeenMade(){
		return this.moveMade;
	}
	
	private void moveHasBeenMade(){
		this.moveMade = true;
	}
		
	private boolean readyToEndTurn(){
		return this.turnSubmit;
	}
	
	private void setEndOfTurn(){
		this.turnSubmit = true;
	}
	
	private void setBeginingOfTurn(){
		this.turnSubmit = false;
		this.moveMade = false;
	}
	
	private boolean playSound(){
		return this.soundIsOn;
	}
}
