package com.cosmichorizons.gameparts;
//import java.awt.Color;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.cosmichorizons.basecomponents.BoardComponent;
import com.cosmichorizons.basecomponents.Coordinate;
import com.cosmichorizons.basecomponents.MovableObject;
import com.cosmichorizons.characters.EmptyObject;
import com.cosmichorizons.characters.OneUnit;
import com.cosmichorizons.characters.TaichoUnit;
import com.cosmichorizons.characters.ThreeUnit;
import com.cosmichorizons.characters.TwoUnit;
import com.cosmichorizons.enums.Location;
import com.cosmichorizons.enums.Player;
import com.cosmichorizons.enums.Ranks;
import com.cosmichorizons.enums.TaichoColors;
import com.cosmichorizons.exceptions.BoardComponentNotFoundException;


/**
 * Controls the game data dealing with the board
 * 
 * Functions:
 * 		pieceAt(int row, int col)
 * 		getCoordinateOfId(int id)
 * 		getBoardComponentAtId(int id)
 * 		getSelectedBoardComponent()
 * 		isWithinBufferZone(int bufferZone, BoardComponent bc, BoardComponent pbc)
 * 		getCastleBoardComponents(Player p)
 * @author Ryan
 *
 */
public class TaichoGameData {
	BoardComponent[][] board;
	private Player player1, player2, currentPlayer;
	private boolean gameInPlay = true;

	/**
	 * Constructor. Create the board and set it up for a new game.
	 * Initialize the two players and create a 9*15 playing board
	 */
	public TaichoGameData(Player p1, Player p2) {
		System.out.println("ObjectData constructor");
		player1 = p1;
		player2 = p2;
		board = new BoardComponent[9][15];
		setUpGame();
	}//TaichoGameData

	public TaichoGameData(){
		try{
			player1 = Player.PLAYER_ONE;
			player2 = Player.PLAYER_TWO;
			board = new BoardComponent[9][15];
			setUpGame();
		}catch(Throwable th){
			System.err.println("Error occurred initializing taicho game daat");
			System.err.println(th.getCause());
		}
	}
	/**
	 * Set up the board with characters in position for the beginning of a game.
	 * See docs/board.xls for layout of board.
	 * 
	 * Based on column and row values it creates the Taicho game board with each 
	 * game position being populated by a BoardComponent Object. (The BoardComponent[BC] object 
	 * contains data for the state of each position (occupied?, Location, color, etc...) 
	 * 
	 */
	public void setUpGame() {
		System.out.println("Set Up Game");
		currentPlayer = Player.NONE;
		int index = 0;
		for (int col = 0; col < 15; col++) {
			for (int row = 0; row < 9; row++) {
				if (row == 4 && col == 1) {
					// position is Player One Taicho
					board[row][col] = new BoardComponent(new TaichoUnit(player1), Location.PLAYER_ONE_CASTLE, new Coordinate(col, row, index));
				} else if (row == 4 && col == 13) {
					// position is Player Two Taicho
					board[row][col] = new BoardComponent(new TaichoUnit(player2), Location.PLAYER_TWO_CASTLE, new Coordinate(col, row, index));
				} else if (((col == 10) && (row % 2 == 0))
						|| ((col == 9 || col == 11) && (row % 2 == 1))) {
					// position is Player Two Samurai
					 board[row][col] = new BoardComponent(new OneUnit(player2), Location.GAME_BOARD, new Coordinate(col, row, index));
				} else if (((col == 4) && (row % 2 == 0))
						|| ((col == 3 || col == 5) && (row % 2 == 1))) {
					// position is Player One Samurai
					board[row][col] = new BoardComponent(new OneUnit(player1), Location.GAME_BOARD, new Coordinate(col, row, index));
//					board[row][col] = new BoardComponent(new TwoUnit(player1), Location.GAME_BOARD, new Coordinate(col, row, index));
//					board[row][col] = new BoardComponent(new ThreeUnit(player1), Location.GAME_BOARD, new Coordinate(col, row, index));
				} else if (((row <= 2) && (col <= 2 || col >= 12))
						|| ((row >= 6) && (col <= 2 || col >= 12))) {
					// position is not on board (invisible section
					board[row][col] = new BoardComponent(Location.OUT_OF_BOUNDS, new Coordinate(col, row, index));
				} else if((col <= 2) && (row >= 3 && row <=5)){
					// position is Player One Castle
					board[row][col] = new BoardComponent(Location.PLAYER_ONE_CASTLE, new Coordinate(col, row, index));
				} else if((col >= 12) && (row >= 3 && row <=5)){
					// position is Player Two Castle
					board[row][col] = new BoardComponent(Location.PLAYER_TWO_CASTLE, new Coordinate(col, row, index));
				}
				else {
					// position is empty
					board[row][col] = new BoardComponent(Location.GAME_BOARD, new Coordinate(col, row, index));
				}
				if( ( col == 3 || col == 11 ) && ( row >= 3  && row <= 5) ){
					//Board component is a barrier
					board[row][col].setBarrier(true);
				}
				if(board[row][col].getLocation() != Location.OUT_OF_BOUNDS){
					if (row % 2 == col % 2) {
						board[row][col].setColor(TaichoColors.GAME_BOARD_LIGHT.getColor());
					} else {
						board[row][col].setColor(TaichoColors.GAME_BOARD_DARK.getColor());
					}
				}else{
					board[row][col].setColor(Color.BLACK);
				}
				index++;
			}
		}
		
	} // end setUpGame()

	public void nextTurn(Player nextPlayer){
		if( nextPlayer != Player.NONE){
			if( nextPlayer == Player.PLAYER_ONE ){
				System.out.println("Starting player ones turn");
				this.currentPlayer = Player.PLAYER_ONE;
			}else if( nextPlayer == Player.PLAYER_TWO ){
				System.out.println("Starting player twos turn");
				this.currentPlayer = Player.PLAYER_TWO;
			}
		}
	}
	/**
	 * Return the BC of the square in the specified row and column.
	 * If row or column are out of bounds a 'BoardComponentNotFoundException' is thrown
	 */
	public BoardComponent pieceAt(int row, int col) {
		if(row > 8 || col > 14){
			throw new BoardComponentNotFoundException();
		}else{
			return board[row][col];
		}
	}
	
	public BoardComponent componentFromCoord(Coordinate coor){
//		BoardComponent bc = null;
		if(coor.getPosX() > 14 || coor.getPosY() > 8){
			throw new BoardComponentNotFoundException();
		}else{
			return board[coor.getPosY()][coor.getPosX()];
		}
//		return bc;
	}
	/**
	 * Returns the Coordinate object of the BC found to have the 
	 * same id as the @param
	 * @param id
	 * @return
	 */
	public Coordinate getCoordinateOfId(int id){
		for (int col = 0; col < 15; col++) {
			for (int row = 0; row < 9; row++) {
				if(board[row][col].getCoordinate().getId() == id){
					return board[row][col].getCoordinate();
				}
			}
		}
		return new Coordinate();//new BoardComponent(null, null);
	}
	
	/**
	 * Returns the BC object of the BC found to have the 
	 * same id as the @param
	 * @param id
	 * @return
	 */
	public BoardComponent getBoardComponentAtId(int id) {
		for (int col = 0; col < 15; col++) {
			for (int row = 0; row < 9; row++) {
				if(board[row][col].getCoordinate().getId() == id){
//					System.out.println("found BoardComponent of id - " + id + " at " + board[row][col].getCoordinate());
					return board[row][col];
				}
			}
		}
				
		throw new BoardComponentNotFoundException();
	}
	
	/**
	 * Returns the single BC on the board that has its selected member set to true.
	 * If one is not found then throw a BoardComponentNotFoundException is thrown
	 * same id as the @param
	 * @param id
	 * @return
	 */
	public BoardComponent getSelectedBoardComponent(){
		for (int col = 0; col < 15; col++) {
			for (int row = 0; row < 9; row++) {
				if( board[row][col].isSelected()){
					return board[row][col];
				}
			}
		}
		throw new BoardComponentNotFoundException();
	}
	
	/**
	 * This method is used to makes sure that moves do not wrap around the board. 
	 * It makes sure that selectedBc and potentialBc are within the buffer zone for the appropriate chracter
	 * Returns true if potentialBc.row is within +/-bufferZone of selectedBc.row && 
	 * 		potentialBc.col is within +/-bufferZone of selectedBc.col 
	 * 
	 * The buffer zone is the maximum number of rows/cols that an object can move for its designated Rank
	 * lvl1 bufferZone - 1
	 * lvl2 bufferZone - 2
	 * lvl3 bufferZone - 3
	 * @param bufferZone
	 * @param selectedBc
	 * @param potentialBc
	 * @return
	 */
	public boolean isWithinBufferZone(int bufferZone, BoardComponent selectedBc, BoardComponent potentialBc){
		Coordinate selectedCoor = selectedBc.getCoordinate();
		Coordinate potentialCoor = potentialBc.getCoordinate();
		if( (( potentialCoor.getPosY() <= (selectedCoor.getPosY() + bufferZone) ) && ( potentialCoor.getPosY() >= (selectedCoor.getPosY() - bufferZone))) &&
				(( potentialCoor.getPosX() <= (selectedCoor.getPosX() + bufferZone) ) && ( potentialCoor.getPosX() >= (selectedCoor.getPosX() - bufferZone))) ){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * returns all BC's contained in the castle of player 'p'
	 * @param p
	 * @return
	 */
	public ArrayList<BoardComponent> getCastleBoardComponents(Player p){
		ArrayList<BoardComponent> castleBc = new ArrayList<BoardComponent>();
		for (int col = 0; col < 15; col++) {
			for (int row = 0; row < 9; row++) {
				if( board[row][col].getLocation() == Location.PLAYER_ONE_CASTLE && p == Player.PLAYER_ONE ){
					castleBc.add(board[row][col]);
				}else if( board[row][col].getLocation() == Location.PLAYER_TWO_CASTLE && p == Player.PLAYER_TWO ){
					castleBc.add(board[row][col]);
				}
			}
		}
		return castleBc;
	}
	
	public Player getCurrentPlayer(){
		return this.currentPlayer;
	}
	public void setCurrentPlayer(Player currPlyr){
		this.currentPlayer = currPlyr;
	}
	public String getCurrentPlayerString(){
		String plyr = "NONE";
		if(this.currentPlayer == Player.PLAYER_ONE){
			plyr = "Player One";
		}else if(this.currentPlayer == Player.PLAYER_TWO){
			plyr = "Player Two";
		}
		return plyr;
	}
	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}	
	
	public boolean isGameInPlay(){
		return this.gameInPlay;
	}
	
	/**
     * Move units from one BC to another. params are coordinates of new location. 
     * @param coor
     */
    public void makeMove(Coordinate coor){
    	System.out.println("makeMove");
    	BoardComponent bc = pieceAt(coor.getPosY(), coor.getPosX());
    	BoardComponent selectedBc = getSelectedBoardComponent();
    	if(!bc.isOccupied() && bc.getLocation() != Location.OUT_OF_BOUNDS){
    		System.out.println("square IS NOT occupied");
    		MovableObject temp = selectedBc.removeCharacter();
    		bc.setCharacter( temp );
    	}
    	selectedBc.setSelected(false);
    }
    
	/**
     * This method is called to stack one character on top of another character, of the same player. 
     * After some validation it will remove the character from its original place and replace it 
     * with a character of one higher rank
     * @param row
     * @param col
     * @return
     */
    public boolean stackUnits(Coordinate coor){
    	System.out.println("stack units");
    	boolean success = true;
    	BoardComponent bc;
    	BoardComponent selectedBc; 
    	try{
	    	bc = pieceAt(coor.getPosY(), coor.getPosX());
	    	selectedBc = getSelectedBoardComponent();
	    	Player p = selectedBc.getCharacter().getPlayer();
	    	if( bc.isOccupied() && selectedBc.isOccupied() ){ //make sure both are occupied
	    		if( bc.getCharacter().getPlayer() == p ){ //and belong to the same player
	    			if( !(bc.getCharacter().getRank() == Ranks.LEVEL_THREE || selectedBc.getCharacter().getRank() == Ranks.LEVEL_THREE) ){ //&& //not level three 
	    					if( !(bc.getCharacter().getRank() == Ranks.LEVEL_TWO && selectedBc.getCharacter().getRank() == Ranks.LEVEL_TWO) ){// ){ //or both are not level two
	    				if( bc.getCharacter().getRank() != Ranks.TAICHO && selectedBc.getCharacter().getRank() != Ranks.TAICHO ){			//neither are a taicho
	    					MovableObject selectedChar = selectedBc.removeCharacter();
	    					MovableObject joiningChar = bc.removeCharacter();
	    					MovableObject newChar = new EmptyObject();
	    					if( selectedChar.getRank() == Ranks.LEVEL_ONE && joiningChar.getRank() == Ranks.LEVEL_ONE ){
	    						newChar = new TwoUnit(p, selectedChar, joiningChar);
	    					}else if( (selectedChar.getRank() == Ranks.LEVEL_ONE && joiningChar.getRank() == Ranks.LEVEL_TWO) ||
	    							(selectedChar.getRank() == Ranks.LEVEL_TWO && joiningChar.getRank() == Ranks.LEVEL_ONE) ){
	    						newChar = new ThreeUnit(p, selectedChar, joiningChar);
	    					}
	    					bc.setCharacter(newChar);
	    					selectedBc.setSelected(false);
//	    					return true;
	    					success = true;
	    				}else{
		    				success = false;
		    			}
	    			}else{
	    				success = false;
	    			}
	    			}else{
	    				success = false;
	    			}
	    		}else{
	    			success = false;
	    		}
	    	}else{
//	    		return false;
	    		success = false;
	    	}
//	    	return false;
//	    	success = false;
    	}catch(BoardComponentNotFoundException bcnfe){
    		System.err.print("Exception thrown while attempting to stack units  ::: ");
    	}
    	return success;
    }
    
    /**
	 * Method handles combat. verifies that the characters are able to battle
	 * @param row
	 * @param col
	 * @return
	 */
//	public boolean attackObject(Coordinate coor){
    public boolean attackObject(BoardComponent victimBc){
		System.out.println("Look at me ma, I'm attacking!!!");
//    	BoardComponent victimBc = pieceAt(coor.getPosY(), coor.getPosX());
    	BoardComponent attackingBc = getSelectedBoardComponent();
    	if(victimBc.isOccupied() && attackingBc.isOccupied()){			//verify both squares are occupied
    		MovableObject victimCharacter = victimBc.getCharacter();
    		MovableObject oppressingCharacter = attackingBc.getCharacter();
    		if(victimCharacter != oppressingCharacter && //must be opposite players and not a 'NONE' Player
    				(victimCharacter.getPlayer() != Player.NONE && oppressingCharacter.getPlayer() != Player.NONE)){	
    			System.out.println("Uh oh, looks like someone may be getting attacked...");
    			if(victimBc.isAttackable()){
    				System.out.println("Yup, not looking good for you :: " + victimBc.getCharacter().toString());
	    			if(oppressingCharacter.getCombatValue() >= victimCharacter.getCombatValue() && victimCharacter.getRank() != Ranks.TAICHO){
	    				System.out.println("...Bummer, you've been attacked -- " + victimBc.getCharacter().toString());
	    				victimBc.removeCharacter(); //dead
	    				victimBc.setCharacter( attackingBc.removeCharacter() );
	    				attackingBc.setSelected(false);
	    				return true;
	    			}else if(oppressingCharacter.getCombatValue() >= victimCharacter.getCombatValue() && victimCharacter.getRank() == Ranks.TAICHO){
	    				// A taicho character has been killed, game is over
	    				System.out.println("Game is over, taicho is dead");
	    				this.gameInPlay = false;
    				}else{
	    				//attacking character can beat victim using teammates
	    				System.out.println("Multiple samurais are about to kill you...");
	    				victimBc.removeCharacter();
	    				victimBc.setCharacter( attackingBc.removeCharacter() );
	    				attackingBc.setSelected(false);
	    				return true;
	    			}
    			}
    		}else{
    			//players were not right
    			System.err.print("You cant attack your own team!");
    			return false;
    		}
    	}else{
    		//one or both BC's are not occupied
    		System.err.print("You cant attack empty squares");
    		return false;
    	}
		return false;
	}
	
	/**
     * Does basically the opposite of makeMove.
     * @param row
     * @param col
     * @return
     */
    public boolean unstackUnits(Coordinate coor){
    	BoardComponent bc = pieceAt(coor.getPosY(), coor.getPosX());
    	BoardComponent selectedBc = getSelectedBoardComponent();
    	Player p = selectedBc.getCharacter().getPlayer();
    	if(!bc.isOccupied() && bc.getLocation() != Location.OUT_OF_BOUNDS){
    		System.out.println("square IS NOT occupied");
    		Ranks r = selectedBc.getCharacter().getRank();
    		switch(r){
				case LEVEL_TWO:
					TwoUnit selectedTwoUnit = (TwoUnit) selectedBc.getCharacter();
					MovableObject mo21 = selectedTwoUnit.removeUnitFromStack();
					MovableObject mo20 = selectedTwoUnit.removeUnitFromStack();
					bc.setCharacter( mo21 );
					selectedBc.setCharacter( mo20 );
					break;
				case LEVEL_THREE:
					ThreeUnit selectedThreeUnit = (ThreeUnit) selectedBc.getCharacter();
					MovableObject mo32 = selectedThreeUnit.removeUnitFromStack();
					MovableObject mo31 = selectedThreeUnit.removeUnitFromStack();
					MovableObject mo30 = selectedThreeUnit.removeUnitFromStack();
					bc.setCharacter( mo32 );
					selectedBc.setCharacter( new TwoUnit( p, mo31, mo30 ) );
					break;
				case LEVEL_ONE:
				case TAICHO:
				case NONE:
				default:	//no need to do anything
					System.err.println("character is not able to be unstacked");
					break;
    		}
    	}
    	selectedBc.setSelected(false);
    	return true;
    }
}
