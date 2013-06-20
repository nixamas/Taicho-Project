package com.cosmichorizons.taicho;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AlertWindow {
	private Taicho _game;
	private TextureRegion _background;
	private String _text;
	private LinkedList _textLines;
	private BitmapFont _font;
	protected Vector2 _pos;
	protected int _width;
	protected int _height;
	protected Button _okButton, _cancelButton;
	private boolean hidden = true;
	private Use myCurrentUse = Use.NONE; 	//used to figure out which instance of the window is currently open
	public enum Use {	//add a enum for anytime this popup window is used
		NONE,
		ResumeSaveGame,
		SaveCurrentGame
	};

//	public AlertWindow(Taicho game,
//				  int x,
//				  int y,
//				  String text,
//				  TextureRegion background,
//				  BitmapFont font, Button posButton, Button negButton) {
//		_game = game;
//		_background = background;
//		_font = font;
//		_text = text;
//		_pos = new Vector2(x, y);
//		_width = 400;
//		_height = 260;
//		_okButton = posButton;
//		_cancelButton = negButton;
//	}
	
	public AlertWindow(Taicho game,
			  int x,
			  int y,
			  String text,
			  TextureRegion background,
			  TextureRegion buttonBackground,
			  TextureRegion buttonBackgroundClicked, 
			  BitmapFont font,
			  AlertWindow.Use myUse) {
		_game = game;
		_background = background;
		_font = font;
		_text = text;
		_pos = new Vector2(x, y);
		_width = 400;
		_height = 260;
		_okButton = new Button(_game, (int)_pos.x, ((int)_pos.y + 200), "OK");
		_okButton.setFont(_font);
		_okButton.setBackground( buttonBackground );
		_okButton.setBackgroundClicked( buttonBackgroundClicked );
		_cancelButton = new Button( _game, (int)_pos.x + 200, (int)_pos.y + 200, "Cancel" );
		_cancelButton.setFont(_font);
		_cancelButton.setBackground( buttonBackground );
		_cancelButton.setBackgroundClicked( buttonBackgroundClicked );
		myCurrentUse = myUse;
		
		beautifyString();
	}
	
	private void beautifyString(){
		LinkedList<String> lines = new LinkedList<String>();
		String [] words = _text.split(" ");
		StringBuffer sb = new StringBuffer();
		for( String word : words ){
			if( (sb.length() + word.length()) <= 20 ){
				sb.append( word + " " );
			}else{
				lines.add( sb.toString() );
				sb.setLength(0);
				sb.append(word + " ");
			}
		}
		if( sb.length() > 0 ){
			lines.add( sb.toString() );
		}
		_textLines = lines;
	}
	
//	public AlertWindow(Taicho game, int x, int y, String text, Button posButton, Button negButton) {
//		_game = game;
//		_text = text;
//		_background = null;
//		_font = null;
//		
//		_pos = new Vector2(x, y);
//		_okButton = posButton;
//		_cancelButton = negButton;
//	}
	private String justifyString(String s){
		boolean front = true;
		while( s.length() <= 20){
			if( front ){	//insert space at front
				s = " " + s;
			}else if( !front ){		// insert space at end
				s = s + " ";
			}
			front = !front;		//toggle
		}
		return s;
	}
	
	public void render() {
		if( !hidden ){
			SpriteBatch batch = _game.getSpriteBatch();
			
			if (_background != null)
			{
				batch.draw(_background, _pos.x, _pos.y);
			}
			
//			if (_font != null)
//			{
//				_font.draw(batch, _text, _pos.x + 10, _pos.y + 10);
//			}
			if( _textLines != null ){
				for( int i = 0 ; i < _textLines.size() ; i++ ){
					_font.draw(batch, justifyString( _textLines.get(i).toString() ) , _pos.x + 10, _pos.y + ( 35 * (i + 1) ));
				}
			}
			
			if ( _okButton != null ){
				_okButton.render();
			}
			if ( _cancelButton != null ){
				_cancelButton.render();
			}
		}// !hidden
	}
	
	public int getX() {
		return (int)_pos.x;
	}
	
	public int getY() {
		return (int)_pos.y;
	}
	
	public void setX(int x) {
		_pos.x = x;
	}
	
	public void setY(int y) {
		_pos.y = y;
	}
	
	public void setPosition(int x, int y) {
		_pos.x = x;
		_pos.y = y;
	}
	
	public String getText() {
		return _text;
	}
	
	public void setText(String text) {
		_text = text;
		beautifyString();
	}

	public void setPosButtonText(String text) {
		this._okButton.setText(text);
	}
	
	public void setNegButtonText(String text) {
		this._cancelButton.setText(text);
	}
	
	public void setBackground(TextureRegion background) {
		_background = background;
		
		if (background != null) {
			_width = _background.getRegionWidth();
			_height = -_background.getRegionHeight();
		}
	}
	
	public void setFont(BitmapFont font) {
		_font = font;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public void close(){
		setHidden(true);
		setText("");
		this._okButton.setText("");
		this._cancelButton.setText("");
	}
	
	public boolean isPositiveButtonClicked(int x, int y){
		return this._okButton.isClicked(x, y);

	}
	
	public boolean isNegativeButtonClicked(int x, int y){
		return this._cancelButton.isClicked(x, y);
	}

	public Use getMyCurrentUse() {
		return myCurrentUse;
	}

	public void setMyCurrentUse(Use myCurrentUse) {
		this.myCurrentUse = myCurrentUse;
	}
	
}
