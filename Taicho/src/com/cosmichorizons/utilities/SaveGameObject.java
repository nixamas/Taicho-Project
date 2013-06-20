package com.cosmichorizons.utilities;

import java.util.LinkedList;

import com.cosmichorizons.basecomponents.BoardComponent;
import com.cosmichorizons.basecomponents.Coordinate;
import com.cosmichorizons.basecomponents.MovableObject;
import com.cosmichorizons.characters.OneUnit;
import com.cosmichorizons.characters.TaichoUnit;
import com.cosmichorizons.characters.ThreeUnit;
import com.cosmichorizons.characters.TwoUnit;
import com.cosmichorizons.enums.Player;
import com.cosmichorizons.enums.Ranks;
import com.cosmichorizons.gameparts.TaichoGameData;

public class SaveGameObject {
	private LinkedList<UnitSaveObject> saveParts;
	private Player currentPlayer = Player.NONE;
	private String MEM_SEP = "@";
	private String UNIT_SEP = "%";
	private String LIST_START = "[";
	private String LIST_END = "]";
	private String CURRENT_PLAYER = "CURRENT_PLAYER";
	
	public SaveGameObject(TaichoGameData taichoGameData){
		saveParts = new LinkedList<UnitSaveObject>();
		currentPlayer = taichoGameData.getCurrentPlayer();
		for (int col = 0; col < 15; col++) {
			for (int row = 0; row < 9; row++) {
				BoardComponent bc = taichoGameData.pieceAt(row, col);
				if( bc.isOccupied() ){
					saveParts.add( new UnitSaveObject( bc ) );
				}
			}
		}
		
	}	
	
	public SaveGameObject( String saveFileContents ){
		saveParts = new LinkedList<UnitSaveObject>();
		String currPlayerString = saveFileContents.substring(1, saveFileContents.indexOf(this.LIST_END + this.LIST_START));	//get current player data
		saveFileContents = saveFileContents.replace(this.LIST_START + currPlayerString + this.LIST_END, "");	//remove current player data
		currPlayerString = currPlayerString.replace(this.CURRENT_PLAYER + ":", "");		//remove current player key string
		currentPlayer = Player.valueOf( currPlayerString );	//set player enum
		saveFileContents = saveFileContents.substring(1, saveFileContents.length() - 1 );
		String [] elems = saveFileContents.split( this.UNIT_SEP );
		for( String elem : elems ){
			String [] mems = elem.split( this.MEM_SEP );
			Coordinate coor = null;
			MovableObject mo = null;
			for( String param : mems ){
				param = param.substring(1, param.length() - 1 );
				String className = param.split(":")[0];
				if( "Coordinate".equalsIgnoreCase(className) ){
					coor = parseCoordinateString( param.split(":")[1]);
				}else if( "MovableObject".equalsIgnoreCase(className) ){
					mo = paraseMovableObjString( param.split(":")[1]);
				}
			}
			saveParts.add( new UnitSaveObject( new BoardComponent(mo, TaichoGameData.getLocationFromBoardComponentId( coor.getId() ), coor) ) );
		}
	}
	
	private Coordinate parseCoordinateString( String cont ){
		// "{Coordinate:[id=" + id + ", posX=" + posX + ", posY=" + posY + "]}"
		String [] fields = ((String) cont.subSequence( 1,  cont.length() - 1 )).split(",");
		return new Coordinate(Integer.parseInt(fields[1].trim().replace("posX=", "")), Integer.parseInt(fields[2].trim().replace("posY=", "")), Integer.parseInt(fields[0].trim().replace("id=", "")) );
	}
	
	private MovableObject paraseMovableObjString( String cont ){
		//  "{MovableObject:[player=" + player + ", rank=" + rank + "]}"
		String [] fields = ((String) cont.subSequence( 1,  cont.length() - 1 )).split(",");
		Player p = Player.NONE;
		Ranks r = Ranks.NONE;
		MovableObject moveObj = null;
		if( fields[0].contains("player")){
			String player = fields[0].trim().replace("player=", "");
			p = Player.valueOf( player );
		}
		if ( fields[1].contains("rank")){
			String rank = fields[1].trim().replace("rank=", "");
			r = Ranks.valueOf( rank );
		}
		switch( r ){
		case TAICHO:
			moveObj = new TaichoUnit(p);
			break;
		case LEVEL_ONE:
			moveObj = new OneUnit(p);
			break;
		case LEVEL_TWO:
			moveObj = new TwoUnit(p);
			break;
		case LEVEL_THREE:
			moveObj = new ThreeUnit(p);
			break;
		default:
			break;
		}
		return moveObj;
	}
	
	public String getUnitSaveObjectList(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.LIST_START);
		sb.append( this.CURRENT_PLAYER + ":" + this.currentPlayer.getName() );
		sb.append(this.LIST_END + this.LIST_START);
		
		for( UnitSaveObject elem : saveParts ){
			sb.append( elem.getCharacter().toSaveString() + this.MEM_SEP + elem.getCoordinate().toSaveString() + this.UNIT_SEP );
		}
		String tmp = sb.substring(0, sb.length() - 1 );
		tmp += this.LIST_END;
		return tmp;
	}
	
	public Coordinate getCoordinateOfElementWithId(int id){
		return getElementById(id).getCoordinate();
	}
	public MovableObject getCharacterOfElementWithId(int id){
		return getElementById(id).getCharacter();
	}
	private UnitSaveObject getElementById(int id){
		UnitSaveObject elem = null;
		for(int i = 0 ; i < saveParts.size() ; i++ ){
			if( id == saveParts.get(i).getCoordinate().getId() ){
				elem = saveParts.get(i);
			}
		}
		return elem;
	}
	
	public boolean isIndexOfSavedElement(int idx){
		boolean present = false;
		for(int i = 0 ; i < saveParts.size() ; i++ ){
			if( idx == saveParts.get(i).getCoordinate().getId() ){
				present = true;
			}
		}
		return present;
	}
	
	public boolean isSavedElementAtCoordinate( Coordinate coor ){
		boolean exists = false;
		for(int i = 0 ; i < saveParts.size() ; i++ ){
			if( saveParts.get(i).getCoordinate().equals( coor ) ){
				exists = true;
			}
		}
		return exists;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public class UnitSaveObject{
		private final Coordinate coordinate;
		private MovableObject character;		//		private boolean highlight;
		public UnitSaveObject(BoardComponent bc){
			this.coordinate = bc.getCoordinate();
			this.character = bc.getCharacter();
		}
		
		public MovableObject getCharacter() {
			return character;
		}
		public void setCharacter(MovableObject character) {
			this.character = character;
		}
		public Coordinate getCoordinate(){
			return this.coordinate;
		}
	}
}
