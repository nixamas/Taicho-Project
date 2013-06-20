package com.cosmichorizons.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.cosmichorizons.gameparts.TaichoGameData;

public class SystemConfiguration {
	private String GAME_IN_PROGRESS_KEY = "game_in_progress";
	private String NO_GAME_IN_PROGRESS = "NO_GAME_IN_PROGRESS";
	private String SOUND_STATE_KEY = "sound_on";
	private String MUSIC_STATE_KEY = "music_on";
	private String STATE_ON = "true";
	private String STATE_OFF = "false";
	private String NEW_LINE_SEP = "/r/n";
	private String KEY_SEP = ":";
	private boolean sound = false;
	private boolean music = false;
	private boolean in_progress = false;
	
	FileHandle _configFileHandle = null;
	FileHandle _savedGameFileHandle = null;
	
	public static void main(String [] args){
//		SystemConfiguration me = new SystemConfiguration();
//		BoardComponent bc = new BoardComponent(new TaichoUnit(Player.PLAYER_ONE), Location.PLAYER_TWO_CASTLE, new Coordinate(2, 1, 4));
//		StringBuffer sb = new StringBuffer();
//		try {
//			writeObject(bc, sb);
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public SystemConfiguration(){
		System.out.println("SystemConfiguration...");
		boolean windows = false;
		switch( Gdx.app.getType() ){
			case Desktop:
				//assuming Windows
				windows = true;
				this.NEW_LINE_SEP = "\r\n";
			case Android:
				//not sure about android yet
			default:
				break;
		}
		try{
			if( !windows ){
				String extPath = Gdx.files.getExternalStoragePath();
				_configFileHandle = Gdx.files.external( extPath + "files/myconfigurationfile.txt" );
			}else if( windows ){
				_configFileHandle = Gdx.files.local("files\\myconfigurationfile.txt");
			}
			if( !windows ){
				_savedGameFileHandle = Gdx.files.external("files/savedgamefile.txt");
			}else if( windows ){
				_savedGameFileHandle = Gdx.files.local("files\\savedgamefile.txt");
			}
//			String contents = _fileHandle.readString();
			if( _configFileHandle.exists() ){
				parseFileContents( _configFileHandle.readString() );	//fills out parameters
			}else{
				_configFileHandle.writeString(this.DEFAULT_CONFIG_FILE, false);
				parseFileContents( _configFileHandle.readString() );	//fills out parameters
			}
			
			this.in_progress = checkForGameInProgress();
			
		} catch ( Exception e ){
			System.err.println("SystemConfiguration Error :: " + e.getMessage());
		}
	}

	public boolean checkForGameInProgress(){
		String contents = _savedGameFileHandle.readString();
		return !contents.contains( this.NO_GAME_IN_PROGRESS );
	}
	
	public void writeTaichoGameData(TaichoGameData taichoGameData){
		StringBuffer sb = new StringBuffer();
		SaveGameObject saveObj = new SaveGameObject( taichoGameData );
		sb.append( saveObj.getUnitSaveObjectList() );
		_savedGameFileHandle.writeString(sb.toString(), false );
	}
	
	/**
	 * writes a string in the savedgamefile.txt signifying that now game was saved
	 */
	public void writeNoSaveData(){
		_savedGameFileHandle.writeString(this.NO_GAME_IN_PROGRESS, false);
	}
	
	private void writeConfigFile(){
		String contents = _configFileHandle.readString();
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
		_configFileHandle.writeString( newContents , false );
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
	
	public SaveGameObject parseSaveFile(){
		SaveGameObject saveObj = null;
		System.out.println("parsing save file contents");
		String contents = _savedGameFileHandle.readString();
		if( contents.contains( this.NO_GAME_IN_PROGRESS ) ) {
			this.in_progress = false;
		}else{
			this.in_progress = true;
			saveObj = new SaveGameObject( contents/*.substring(1, contents.length() - 1 )*/ );
		}
		return saveObj;
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
	
	private String DEFAULT_CONFIG_FILE = "#<--starts a comment line\r\n#   key:value\r\n#   look @ SystemConfiguration on what uses this file\r\n#\r\ngame_in_progress:true\r\nsound_on:true\r\nmusic_on:false\r\n";

}
