Taicho
======

'Personal' Taicho repository
							           	Column(X)
				 0    1   2   3   4   5   6   7   8   9   10  11  12  13 
				              _   _   _   _   _   _   _   _
			0	  0   10  19| _ | _ | _ | _ | _ | _ | _ | _ |            
			                 --- --- --- --- --- --- --- ---   
			1	  1   11  20| _ | _ | _ | _ | _ | _ | _ | _ |
			                 --- --- --- --- --- --- --- ---       
			2	  2   12  21| _ | _ | _ | _ | _ | _ | _ | _ | 
			     --- --- --- --- --- --- --- --- --- --- --- --- --- --- 
			3	| 3 | 13| _ | _ | _ | _ | _ | _ | _ | _ | _ | _ | _ | _ |
			     --- --- --- --- --- --- --- --- --- --- --- --- --- --- 
Row(y)     	4	| 4 | 14| _ | _ | _ | _ | _ | _ | _ | _ | _ | _ | _ | _ |
			     --- --- --- --- --- --- --- --- --- --- --- --- --- --- 
			5	| 5 | 15| _ | _ | _ | _ | _ | _ | _ | _ | _ | _ | _ | _ |
			                 --- --- --- --- --- --- --- --- 
			6	  6   16    | _ | _ | _ | _ | _ | _ | _ | _ |
			                 --- --- --- --- --- --- --- --- 
			7	  7   17    | _ | _ | _ | _ | _ | _ | _ | _ |      
			                 --- --- --- --- --- --- --- --- 
			8	  8   18    | _ | _ | _ | _ | _ | _ | _ | _ |

This is a representation of the Taicho game board. The Column and Row correspond to X and Y 
	values and the numbers on the board is each board components id. (look at docs/board.xls for all id's)
	
	The Id's are used to quickly see what locations the selected character can move to. This is
	done by simply adding or subtracting a certain value.
		--LevelOneLegalMoves: +/-1, +/-8, +/-9, +/-10
		--LevelTwoLegalMoves: +/-1, +/-2, +/-9, +/-18
		--LevelThreeLegalMoves: +/-8, +/-10, +/-16, +/-20, +/-24, +/-30
		--Taicho Legal Moves are calculated by finding all unoccupied squares in his castle
		
FILE LIST - as of 12/23/2012
basecomponents
	/Taicho/src/basecomponents/BoardComponent.java
		--Each square on the game board corresponds to a BoardComponent(BC). It contains data pertaining to
			the squares state (occupied, character that occupies it, color, selected, stackable, location 
			on game board
	/Taicho/src/basecomponents/Coordinate.java
		--simple way to find a BoardComonent on board. look for X, Y position or by BC id. 
	/Taicho/src/basecomponents/MovableObject.java
		--class that all character objects extends. Deals with character movement. 
	/Taicho/src/basecomponents/ObjectMove.java
		--another class used to simply transfer data. comprised of a starting and finishing Coordinate
characters
		--All character objects extend the MovableObject class. The character object sets the 
			MovableObjects player field and rank at instantiation. Most character modification should 
			be done to the MovableObject.java file.
	/Taicho/src/characters/EmptyObject.java
	/Taicho/src/characters/OneUnit.java
	/Taicho/src/characters/Taicho.java
	/Taicho/src/characters/ThreeUnit.java
	/Taicho/src/characters/TwoUnit.java
enums
	/Taicho/src/enums/LevelOneLegalMoves.java
	/Taicho/src/enums/LevelThreeLegalMoves.java
	/Taicho/src/enums/LevelTwoLegalMoves.java
		--See MoveManager for these 3 enums
	/Taicho/src/enums/Location.java
		--Simple way to give each BC a easy to see field of where it is located on board. 
			Possible Values: GAME_BOARD, OUT_OF_BOUNDS, PLAYER_ONE_CASTLE, PLAYER_TWO_CASTLE
	/Taicho/src/enums/Player.java
		--Possible Values: PLAYER_ONE, PLAYER_TWO, NONE
	/Taicho/src/enums/Ranks.java
		--Possible Values: LEVEL_ONE, LEVEL_TWO, LEVEL_THREE, TAICHO, NONE
exceptions
	/Taicho/src/exceptions/BoardComponentNotFoundException.java
		--simple exception to throw when a BC is not found. can generally be ignored. 
			probably want to wrap in a try/catch though...
gameparts
	/Taicho/src/gameparts/Board.java
		--Interacts with the user. Contains program ActionListener and MouseListener. 
			decides what the user whats to do (select character, move character, stack units, attack, etc...)
		--In constructor of Board there is a function parameter that when changed will change the size 
			of the window. 
	/Taicho/src/gameparts/ProgramMain.java
		--Main of program. creates board. 
	/Taicho/src/gameparts/TaichoGameData.java
		--Brains of the game. Contains board data (positions, game board, characters, players, etc...)
			TaichoGameDatas methods are called by Board.java to give it data to decide appropriate action
interfaces
	/Taicho/src/interfaces/MoveManager.java
		--to come... ask me for now
		--MoveManager is an interface that is implemented by the enums LevelOneLegalMove.java, 
			LevelTwoLegalMove.java, LevelThreeLegalMove.java. These enums contain the +/- values to add to
			a BC id value to find a possible BC to move/attack to. 
				Whaa?? Enums implementing interfaces, that sounds crazy? 
				Dont worry its not too bad.
				Since all characters extend MovableObject and each Level One/Two/Three character has its own
					set of legal moves LevelOneLegalMove, LevelTwoLegalMove, LevelThreeLegalMove) 
					we can simply creating a single array of MoveManagers in a single method and 
					and through a switch statement fill the array with the appropriate Level_X_LegalMove enums.
					We can then use this single array on an array list of MovableObject types that does
					not depend on the type of MovableObject in the array.
					Ask for further explanation...
		--Look in the getPossibleMoves method of the MovableObject class to see how this is used
utilities
	/Taicho/src/utilities/BoardDimensions.java
		--utility class used to easily change the size of the game window. 
	/Taicho/src/utilities/Utils.java
		--utility class used to hold static methods. (contains color mixing methods at the moment)
      

