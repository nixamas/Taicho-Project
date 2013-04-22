package com.cosmichorizons.gameparts;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.cosmichorizons.basecomponents.BoardComponent;
import com.cosmichorizons.basecomponents.Coordinate;
import com.cosmichorizons.basecomponents.MovableObject;
import com.cosmichorizons.characters.EmptyObject;
import com.cosmichorizons.characters.ThreeUnit;
import com.cosmichorizons.characters.TwoUnit;
import com.cosmichorizons.enums.Location;
import com.cosmichorizons.enums.Player;
import com.cosmichorizons.enums.Ranks;
import com.cosmichorizons.exceptions.BoardComponentNotFoundException;
import com.cosmichorizons.utilities.BoardDimensions;
import com.cosmichorizons.utilities.Utils;


/**
 * UI for game. Deals with how the game program interfaces with the user
 *
 */
public class Board extends JPanel implements ActionListener, MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TaichoGameData board; // The data for the checkers board is kept here.
						// This board is also responsible for generating
						// lists of legal moves.

	boolean gameInProgress, unstackObjects, showIcons; // Is a game currently in progress?

	ArrayList<BoardComponent> validMoves; // An array containing the legal moves for the
								// current player.
	Player player1, player2, currentPlayer;

	BoardDimensions boardProperties;
	
	BufferedImage lvl1Img, lvl2Img, lvl3Img, TaichoImg;
	
	BoardComponent selectedBC;
	
	JButton unstackBtn;
	
	/**
	 * Constructor. Create the buttons and label. Creates Listeners for mouse clicks and
	 * for clicks on the buttons. Create the board and start the first game.
	 */
	public Board(JButton unstckBtn) {
		System.out.println("Board constructor");
		
		validMoves = new ArrayList<BoardComponent>();
		boardProperties = new BoardDimensions(27);          ///     <<<<<<<<<<<<<<<<<<<< CHANGE SCREEN SIZE
		showIcons = false;								/// 		<<<<<<<<<<<<<<<<<<<< SET TRUE FOR IMAGE ICONS INSTEAD OF SHAPES
		setBackground(Color.BLACK);
		addMouseListener(this);
		currentPlayer = Player.NONE;
		unstackBtn = unstckBtn;
		unstackBtn.addActionListener(this);
		player1 = Player.PLAYER_ONE;
		player2 = Player.PLAYER_TWO;
		board = new TaichoGameData(player1, player2);
		selectedBC = new BoardComponent(Location.OUT_OF_BOUNDS, new Coordinate(-1, -1, -1));
		setButtonState();
		unstackObjects = false;
	}

	public Board() {
		// TODO Auto-generated constructor stub
System.out.println("Board constructor");
		
		validMoves = new ArrayList<BoardComponent>();
		boardProperties = new BoardDimensions(27);          ///     <<<<<<<<<<<<<<<<<<<< CHANGE SCREEN SIZE
		showIcons = false;								/// 		<<<<<<<<<<<<<<<<<<<< SET TRUE FOR IMAGE ICONS INSTEAD OF SHAPES
//		setBackground(Color.BLACK);
		addMouseListener(this);
		currentPlayer = Player.NONE;
//		unstackBtn = unstckBtn;
//		unstackBtn.addActionListener(this);
		player1 = Player.PLAYER_ONE;
		player2 = Player.PLAYER_TWO;
		board = new TaichoGameData(player1, player2);
		selectedBC = new BoardComponent(Location.OUT_OF_BOUNDS, new Coordinate(-1, -1, -1));
		setButtonState();
		unstackObjects = false;
	}

	/**
	 * Respond to user's click on one of the two buttons.
	 */
	public void actionPerformed(ActionEvent evt) {
		System.out.println("****Button Clicked****");
		 Object src = evt.getSource();
		 if (src == this.unstackBtn){
			 System.out.println("****unstackBtn clicked****");
			 showValidUnstack();
		 }
	}

	/**
	 * Draw checkerboard pattern in gray and lightGray. Draw the checkers. If a
	 * game is in progress, hilite the legal moves.
	 */
	public void paintComponent(Graphics g) {
		int compSize = boardProperties.getComponentSize();
		int charSize = boardProperties.getCharacterDimension();
		int bL = boardProperties.getBoardLength();
		int bW = boardProperties.getBoardWidth();
		int charOffset = boardProperties.getCharacterOffset();
		
		/* Draw a two-pixel black border around the edges of the canvas. Used as player turn indicator */
		System.out.println("Painting on screen Components");
		g.setColor(currentPlayer.getColor()); 		
		/* Draw player indicator box around  */
		g.drawRect(0, 0, bL - 1, bW - 1);
		g.drawRect(1, 1, bL - 3, bW - 3);
				
		/* Draw the squares of the checkerboard and the checkers. */
		for (int col = 0; col < 15; col++) {
			for (int row = 0; row < 9; row++) {
				BoardComponent bc = board.pieceAt(row, col);
				if (bc.getLocation() != Location.OUT_OF_BOUNDS) {

					g.setColor(bc.getColor());				
					g.fillRect(2 + col * compSize, 2 + row * compSize, compSize, compSize);

					if(bc.isOccupied()){
						if(showIcons){	//if showIcons is true then use icons, else draw boxes
							g.setColor(bc.getCharacter().getColor());
							g.fillRect(2 + charOffset + col * compSize, 2 + charOffset + row * compSize, charSize, charSize);

							Ranks r = bc.getCharacter().getRank();
							BufferedImage icon = null;
							switch(r){
								case LEVEL_ONE:
										icon = lvl1Img;
									break;
								case LEVEL_TWO:
										icon = lvl2Img;
									break;
								case LEVEL_THREE:
										icon = lvl3Img;
									break;
								case TAICHO:
										icon = TaichoImg;
									break;
								case NONE:
								default:
									break;
							}
							g.drawImage(icon,  2 + charOffset + col * compSize,  2 + charOffset + row * compSize, charSize - 7, charSize - 7, null);
						}else{
							g.setColor(bc.getCharacter().getColor());
							g.fillRect(2 + charOffset + col * compSize, 2 + charOffset + row * compSize, charSize, charSize);
								//give characters 'hats' and make Taichos different
							if(bc.getCharacter().getRank() == Ranks.TAICHO){
								g.setColor(Utils.blendColor(bc.getCharacter().getColor(), Color.BLACK, 0.3));
							}else{
								g.setColor(Utils.blendColor(bc.getCharacter().getColor(), Color.WHITE, 0.7));
							}
							g.fillRect(2 + charOffset + col * compSize, 2 + charOffset + row * compSize, charSize - charSize/2, charSize - charSize/2);
						}
						
					}
				}else{
					g.setColor(bc.getColor());
					g.fillRect(2 + col * compSize, 2 + row * compSize, compSize, compSize);
				}
			}
		}
	} // end paintComponent()
	
//	private void simulateMouseClick(){
//		/**
//		 * for some reason if you click on the gameboard first it will repaint 2 boards, one offset from the other
//		 * However if you click an empty square before that it will not repaint the board twice.
//		 * This function does that, hopefully can be taken out later
//		 */
////		MouseEvent me = new MouseEvent((Component) this, (int) 0, (long) 0, (int) 0, 10, 10, (int) 0, true);
////		mousePressed(me);
//	}

	/**
	 * the functions controls the state of the 'unstack' button.
	 * When a BC is selected and that BC contains a character of Rank lvl2 or lvl3 
	 * then the button should be visible, else the button should be invisible
	 */
	private void setButtonState(){
		if( this.selectedBC.getLocation() != Location.OUT_OF_BOUNDS && this.selectedBC.isOccupied() ){
			Ranks r = this.selectedBC.getCharacter().getRank();
			if( r == Ranks.LEVEL_TWO || r == Ranks.LEVEL_THREE ){
				System.out.println("Selected BC Character is able to be unstacked");
				this.unstackBtn.setVisible(true);
			}else{
				System.out.println("Selected BC Character Rank is not unstackable");
				this.unstackBtn.setVisible(false);
			}
		}else{
			this.unstackBtn.setVisible(false);
			System.out.println("Selected component is not occupied or is out of bounds");
		}
	}
	
	/**
	 * Respond to a user click on the board. Calculates which BC was selected, if any. 
	 * If a BC is already selected then this method will decide whether the user wants
	 * to makeMove, stackUnits, attack, or unstack objects
	 */
	public void mousePressed(MouseEvent evt) {
		
		int col = (evt.getX() - 2) / boardProperties.getComponentSize();
		int row = (evt.getY() - 2) / boardProperties.getComponentSize();
		System.out.println("mousePressed @ x_pos="+col+":y_pos="+row);
		
		try{
			BoardComponent bc = board.pieceAt(row, col);
			System.err.println("************* BOARD SQUARE DATE *************");
	    	System.out.println("" + bc.toString());
	    	System.err.println("************* BOARD SQUARE DATE *************");
	    	
	    	
			if(bc.getLocation() != Location.OUT_OF_BOUNDS){	//if player clicks on the game board
				if(validMoves.isEmpty()){		//if there is no valid moves (no BC selected)
					System.out.println("valid moves is empty, first click");
					selectBoardComponent(row, col);
					currentPlayer = bc.getCharacter().getPlayer();
					selectedBC = bc;
				}else if( !validMoves.isEmpty() && validSelection(bc) ){
						// if there is a selected BC and user chose a valid BC
					System.out.println("make move to new VALID square");
					try{
			    		BoardComponent selectedBc = board.getSelectedBoardComponent();
			    		
			    		if( bc.isOccupied() && bc.getCharacter().getPlayer() == selectedBc.getCharacter().getPlayer() ){
			    			//both square are occupied by the same player
			    			System.err.println("StackUnits");
			    			stackUnits(row, col);
			    		}else if( bc.isOccupied() && bc.getCharacter().getPlayer() != selectedBc.getCharacter().getPlayer() ){
			    			//both squares are occupied by opposite players
			    			System.out.println("AttackUnits");
			    			attackObject(row, col);
			    		}else if( !bc.isOccupied() ){
			    			if(unstackObjects){
			    				System.err.println("UnstackUnits");
			    				unstackUnits(row, col);
			    				unstackObjects = false;
			    			}else{
			    				System.err.println("MoveUnits");
			    				makeMove(row, col);
			    			}
			    		}
					}catch(BoardComponentNotFoundException bcnfe){
			    		System.err.println(bcnfe.getMessage());
			    	}
					eraseValidMoves();
						//set the selectedBC to some out of bounds location, keep from being null
			    	selectedBC = new BoardComponent(Location.OUT_OF_BOUNDS, new Coordinate(-1, -1, -1));
				}else{
					//user clicked same BC twice, abort the BC selection
					System.out.println("checking if user clicked selected BC again. If so, Abort");
					try{
			    		BoardComponent selectedBc = board.getSelectedBoardComponent();
			    		if(bc.getCoordinate().equals( selectedBc.getCoordinate() )){
			    				//if there is a selected BC then and the user clicked it a second time, 
			    					//Then clear the valid moves array
			    			selectedBc.setSelected(false);
			    			eraseValidMoves();
			    		}
			    		selectedBC = new BoardComponent(Location.OUT_OF_BOUNDS, new Coordinate(-1, -1, -1));
			    	}catch(BoardComponentNotFoundException bcnfe){
			    		System.err.println(bcnfe.getMessage());
			    	}
				}
			}else{
				try{
		    		BoardComponent selectedBc = board.getSelectedBoardComponent();
	    			selectedBc.setSelected(false);
	    			eraseValidMoves();
		    		selectedBC = new BoardComponent(Location.OUT_OF_BOUNDS, new Coordinate(-1, -1, -1));
		    	}catch(BoardComponentNotFoundException bcnfe){
		    		System.err.println(bcnfe.getMessage());
		    	}
			}
		}catch(BoardComponentNotFoundException bcnfe){
			System.err.println(bcnfe.getMessage());
		}
		
		setButtonState();
		repaint();
	}

	/**
     * This is called by mousePressed() when a player clicks on the
     * square in the specified row and col. If this BC is a valid selection 
     * get the valid moves that the selected character could travel
     */
    private void selectBoardComponent(int row, int col) {
    	System.out.println("doClickSquare");
    	BoardComponent bc = board.pieceAt(row, col);
    	if(bc.isOccupied()){
	    	if(validMoves.isEmpty()){
	    		bc.setSelected(true);
	    		System.out.println("you clicked BoardComponent with ID of -- " + bc.getId());
	    		validMoves = bc.getCharacter().getPossibleMoves(board, bc);
	    	}
    	}
       	
       board.getCoordinateOfId(bc.getId());
       board.getBoardComponentAtId(bc.getId());
       repaint();
    }  // end doClickSquare()
       
    /**
     * Move units from one BC to another. params are coordinates of new location. 
     * @param row
     * @param col
     */
    private void makeMove(int row, int col){
    	System.out.println("makeMove");
    	BoardComponent bc = board.pieceAt(row, col);
    	BoardComponent selectedBc = board.getSelectedBoardComponent();
    	if(!bc.isOccupied() && bc.getLocation() != Location.OUT_OF_BOUNDS){
    		System.out.println("square IS NOT occupied");
    		MovableObject temp = selectedBc.removeCharacter();
    		bc.setCharacter( temp );
    	}
    	selectedBc.setSelected(false);
    	repaint();
    }
    
    /**
     * This method is called to stack one character on top of another character, of the same player. 
     * After some validation it will remove the character from its original place and replace it 
     * with a character of one higher rank
     * @param row
     * @param col
     * @return
     */
    private boolean stackUnits(int row, int col){
    	System.out.println("stack units");
    	BoardComponent bc = board.pieceAt(row, col);
    	BoardComponent selectedBc = board.getSelectedBoardComponent();
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
    					repaint();
    					return true;
    				}
    			}
    			}
    		}
    	}else{
    		return false;
    	}
    	repaint();
    	return false;    	
    }
    
    /**
     * shows the valid moves for a unstacking of a character.
     * @return
     */
    private boolean showValidUnstack(){
    	System.out.println("showValidUnstack method");
    	eraseValidMoves();
    	unstackObjects = true;
    	BoardComponent selectedBc = board.getSelectedBoardComponent();
    	validMoves = selectedBc.getCharacter().getPossibleUnstackLocations(board, selectedBc);
    	repaint();
    	return true;
    }
    
    /**
     * Does basically the opposite of makeMove.
     * @param row
     * @param col
     * @return
     */
    private boolean unstackUnits(int row, int col){
    	System.out.println("UNSTACKING CHARACTER UNIT " + selectedBC.toString() + " @ " + selectedBC.getCoordinate().toString());
    	BoardComponent bc = board.pieceAt(row, col);
    	BoardComponent selectedBc = board.getSelectedBoardComponent();
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
    	repaint();
    	return true;
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
     * Returns the BoardDimensions member of the Board.java class
     * @return
     */
	public BoardDimensions getBoardProperties() {
		return boardProperties;
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
	 * Method handles combat. verifies that the characters are able to battle
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean attackObject(int row, int col){
    	BoardComponent victimBc = board.pieceAt(row, col);
    	BoardComponent attackingBc = board.getSelectedBoardComponent();
    	if(victimBc.isOccupied() && attackingBc.isOccupied()){			//verify both squares are occupied
    		MovableObject victimCharacter = victimBc.getCharacter();
    		MovableObject oppressingCharacter = attackingBc.getCharacter();
    		if(victimCharacter != oppressingCharacter && //must be opposite players and not a 'NONE' Player
    				(victimCharacter.getPlayer() != Player.NONE && oppressingCharacter.getPlayer() != Player.NONE)){		
    			if(victimBc.isAttackable()){
	    			if(oppressingCharacter.getCombatValue() >= victimCharacter.getCombatValue() && victimCharacter.getRank() != Ranks.TAICHO){
	    				System.out.println("...Bummer, you've been attacked -- " + victimBc.getCharacter().toString());
	    				victimBc.removeCharacter(); //dead
	    				victimBc.setCharacter( attackingBc.removeCharacter() );
	    				attackingBc.setSelected(false);
	    				return true;
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
    			return false;
    		}
    	}else{
    		//one or both BC's are not occupied
    		return false;
    	}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * NEEDED BECAUSE CLASS IMPLEMENTS ActionListener, AND MouseListener
	 */
	public void mouseReleased(MouseEvent evt) {	}
	/**
	 * NEEDED BECAUSE CLASS IMPLEMENTS ActionListener, AND MouseListener
	 */
	public void mouseClicked(MouseEvent evt) {	}
	/**
	 * NEEDED BECAUSE CLASS IMPLEMENTS ActionListener, AND MouseListener
	 */
	public void mouseEntered(MouseEvent evt) {	}
	/**
	 * NEEDED BECAUSE CLASS IMPLEMENTS ActionListener, AND MouseListener
	 */
	public void mouseExited(MouseEvent evt) {	}
}
