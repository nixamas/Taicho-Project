package com.cosmichorizons.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.cosmichorizons.gameparts.TaichoGameData;

public class SystemConfiguration {
	private String GAME_IN_PROGRESS_KEY = "game_in_progress";
	private String SOUND_STATE_KEY = "sound_on";
	private String MUSIC_STATE_KEY = "music_on";
	private String STATE_ON = "true";
	private String STATE_OFF = "false";
	private String NEW_LINE_SEP = "/r/n";
	private String KEY_SEP = ":";
	private boolean sound = false;
	private boolean music = false;
	private boolean in_progress = false;
	
	FileHandle _fileHandle = null;
	
	public static void main(String [] args){

	}
	
	public SystemConfiguration(){
		System.out.println("SystemConfiguration...");
		switch( Gdx.app.getType() ){
			case Desktop:
				//assuming Windows 
				this.NEW_LINE_SEP = "\r\n";
			case Android:
				//not sure about android yet
			default:
				break;
		}
		try{
			_fileHandle = Gdx.files.local("myconfigurationfile.txt");
//			String contents = _fileHandle.readString();
			parseFileContents( _fileHandle.readString() );	//fills out parameters
//			setSound(true);
//			setMusic(true);
//			setGameInProgress(true);
//			writeConfigFile();
//			System.out.println("file contents :: " + contents);
//			Gdx.app.exit();
		} catch ( Exception e ){
			System.err.println("SystemConfiguration Error :: " + e.getMessage());
		}
	}
		
	private void writeConfigFile(){
		String contents = _fileHandle.readString();
		StringBuffer sbContents = new StringBuffer();
		String [] lines = contents.split( this.NEW_LINE_SEP );
		for( String line : lines ){
			if( line.startsWith( "#" ) ){	//comment line
				sbContents.append( line );
			}else if( line.contains( this.KEY_SEP)){
				String [] params = line.split( this.KEY_SEP );
				String key = params[0].trim();
				//check the key and store in member
				if( this.GAME_IN_PROGRESS_KEY.equalsIgnoreCase( key )){
					sbContents.append( getKeyBoolValString(key, isGameInProgress() ) );
				}else if( this.SOUND_STATE_KEY.equalsIgnoreCase( key )){
					sbContents.append( getKeyBoolValString(key, isSoundSet() ) );
				}else if( this.MUSIC_STATE_KEY.equalsIgnoreCase( key )){
					sbContents.append( getKeyBoolValString(key, isMusicSet() ) );
				}
			}
			sbContents.append( this.NEW_LINE_SEP );
		}
		String newContents = sbContents.toString();
		_fileHandle.writeString( newContents , false );
	}
	
	private String getKeyBoolValString(String key, boolean val){
		StringBuffer sb = new StringBuffer();
		sb.append( key + this.KEY_SEP + val );
		return sb.toString();
	}

	private void parseFileContents(String data){
		System.out.println("parsing config file contents");
		String [] lines = data.split(this.NEW_LINE_SEP);
		for( int i = 0 ; i < lines.length ; i++){
			if( lines[i].contains( this.KEY_SEP ) && !lines[i].startsWith("#") ){
				String [] params = lines[i].split( this.KEY_SEP );
				String key = params[0].trim();
				String val = params[1].trim();
				//check the key and store in obj
				if( this.GAME_IN_PROGRESS_KEY.equalsIgnoreCase( key )){	//dont call 'set' as it will write config file
					this.in_progress =  setBooleanVal( val );
				}else if( this.SOUND_STATE_KEY.equalsIgnoreCase( key )){
					this.sound = setBooleanVal( val );
				}else if( this.MUSIC_STATE_KEY.equalsIgnoreCase( key )){
					this.music =  setBooleanVal( val );
				}
			}
		}
	}
	
	private boolean setBooleanVal(String val){
		boolean state = false;
		if( this.STATE_ON.equalsIgnoreCase( val ) ){
			state = true;
		}else if( this.STATE_OFF.equalsIgnoreCase( val ) ){
			state = false;
		}
		return state;
	}
	
	public boolean isSoundSet() {
		return sound;
	}
	public void setSound(boolean sound) {
		this.sound = sound;
		writeConfigFile();
	}
	public boolean isMusicSet() {
		return music;
	}
	public void setMusic(boolean music) {
		this.music = music;
		writeConfigFile();
	}
	public boolean isGameInProgress() {
		return in_progress;
	}
	public void setGameInProgress(boolean in_progress) {
		this.in_progress = in_progress;
		writeConfigFile();
	}
	
}
